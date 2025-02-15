package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserProfileImageRequest;

/**
 * Update user profile image port.
 */
public interface UpdateUserProfileImagePort {
    /**
     * Update user profile image.
     *
     * @param request the update user profile image request.
     * @return the updated user
     */
    UserDto update(UpdateUserProfileImageRequest request);

    /**
     * Update the current user profile image.
     *
     * @param request the update user profile image request.
     * @return the updated user as a JWT
     */
    JwtDto updateCurrentUser(UpdateUserProfileImageRequest request);
}
