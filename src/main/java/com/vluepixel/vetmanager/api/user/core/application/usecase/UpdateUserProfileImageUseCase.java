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
 * Update user profile image use case.
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

        User updatedUser = updateHelper(request);

        log.info("User image profile updated");

        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserProfileImageRequest request) {
        MDC.put("operationId", "User id " + request.getUserId());
        log.info("Updating current user image profile");

        User updatedUser = updateHelper(request);

        String jwt = jwtAuthenticationPort.toJwt(updatedUser);

        log.info("Current user image profile updated");

        return new JwtDto(jwt);
    }

    private User updateHelper(UpdateUserProfileImageRequest request) {
        // Delete previous image
        User userToUpdate = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new NotFoundException(User.class, request.getUserId()));

        if (userToUpdate.getProfileImageUrl() != null) {
            deleteImagePort.delete(getImageName(userToUpdate.getProfileImageUrl()));
        }

        // Save the new image
        String savedImageUrl = saveImagePort.save(request.getData(), request.getType());

        // Find the user and update the image
        User updatedUserWithoutImageUrl = userRepository.update(
                request.getUserId(),
                FieldUpdate.set("profileImageUrl", savedImageUrl));

        User updatedUser = userMapper.toBuilder(updatedUserWithoutImageUrl)
                .profileImageUrl(savedImageUrl)
                .build();

        return updatedUser;
    }

    /**
     * Get the image name from the url.
     *
     * @param imageUrl The image url.
     * @return The image name
     */
    private String getImageName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
