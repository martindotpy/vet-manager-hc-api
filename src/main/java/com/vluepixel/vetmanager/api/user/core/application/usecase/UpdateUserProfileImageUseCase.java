package com.vluepixel.vetmanager.api.user.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vluepixel.vetmanager.api.image.core.application.port.in.DeleteImagePort;
import com.vluepixel.vetmanager.api.image.core.application.port.in.SaveImagePort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserProfileImagePort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserProfileImageRequest;

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
    public UserDto update(UpdateUserProfileImageRequest request) {
        MDC.put("operationId", "User id " + request.getUserId());
        log.info("Updating user image profile");
        var result = updateHelper(request);

        return userMapper.toDto(result);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserProfileImageRequest request) {
        MDC.put("operationId", "User id " + request.getUserId());
        log.info("Updating current user image profile");

        var result = updateHelper(request);

        String jwt = jwtAuthenticationPort.toJwt(result);

        return new JwtDto(jwt);
    }

    private User updateHelper(UpdateUserProfileImageRequest request) {
        // Delete previous image
        var userToUpdate = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new NotFoundException(User.class, request.getUserId()));

        if (userToUpdate.getProfileImageUrl() != null) {
            deleteImagePort.delete(getImageIdFromUrl(userToUpdate.getProfileImageUrl()));
        }

        // Save the new image
        var newImageResult = saveImagePort.save(request.getData(), request.getType());

        String imageUrl = newImageResult;

        // Find the user and update the image
        var updatedUserResult = userRepository.update(
                request.getUserId(),
                FieldUpdate.set("profileImageUrl", imageUrl));

        var updatedUser = userMapper.toBuilder(updatedUserResult)
                .profileImageUrl(imageUrl)
                .build();

        return updatedUser;
    }

    private String getImageIdFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
