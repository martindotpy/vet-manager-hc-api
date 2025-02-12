package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.DatabaseShortcuts.rollbackFailure;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.image.core.application.port.in.DeleteImagePort;
import com.vet.hc.api.image.core.application.port.in.SaveImagePort;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.UpdateUserProfileImagePort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.payload.UpdateUserProfileImagePayload;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update user profile image use case
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateUserProfileImageUseCase implements UpdateUserProfileImagePort {
    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final SaveImagePort saveImagePort;
    private final DeleteImagePort deleteImagePort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Result<UserDto, UserFailure> update(UpdateUserProfileImagePayload payload) {
        MDC.put("operationId", "User id " + payload.getUserId());
        log.info("Updating user image profile");
        var result = updateHelper(payload);

        if (result.isFailure()) {
            return failure(result);
        }

        return ok(userMapper.toDto(result.getOk()));
    }

    @Override
    @Transactional
    public Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserProfileImagePayload payload) {
        MDC.put("operationId", "User id " + payload.getUserId());
        log.info("Updating current user image profile");

        var result = updateHelper(payload);

        if (result.isFailure()) {
            return failure(result);
        }

        String jwt = jwtAuthenticationPort.toJwt(result.getOk());

        return ok(JwtDto.builder().jwt(jwt).build());
    }

    private Result<User, UserFailure> updateHelper(UpdateUserProfileImagePayload payload) {
        // Delete previous image
        var userToUpdate = userRepository.findById(payload.getUserId());

        if (userToUpdate.isEmpty()) {
            return rollbackFailure(UserFailure.NOT_FOUND);
        }

        var user = userToUpdate.get();

        if (user.getProfileImageUrl() != null) {
            var deleteImageResult = deleteImagePort.delete(getImageIdFromUrl(user.getProfileImageUrl()));

            if (deleteImageResult.isFailure()) {
                return rollbackFailure(UserFailure.UNEXPECTED);
            }
        }

        // Save the new image
        var newImageResult = saveImagePort.save(payload.getData(), payload.getType());

        if (newImageResult.isFailure()) {
            return rollbackFailure(UserFailure.UNEXPECTED);
        }

        String imageUrl = newImageResult.getOk();

        // Find the user and update the image
        var updatedUserResult = userRepository.update(
                payload.getUserId(),
                FieldUpdate.set("profileImageUrl", imageUrl));

        if (updatedUserResult.isFailure()) {
            return rollbackFailure(UserFailure.UNEXPECTED);
        }

        var updatedUser = userMapper.toBuilder(updatedUserResult.getOk())
                .profileImageUrl(imageUrl)
                .build();

        return ok(updatedUser);
    }

    private String getImageIdFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
