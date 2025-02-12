package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.payload.UpdateUserProfileImagePayload;

/**
 * Update user profile image port.
 */
public interface UpdateUserProfileImagePort {
    /**
     * Update user profile image.
     *
     * @param payload the payload.
     * @return the result.
     */
    UserDto update(UpdateUserProfileImagePayload payload);

    /**
     * Update the current user.
     *
     * @param payload the payload.
     * @return the result
     */
    JwtDto updateCurrentUser(UpdateUserProfileImagePayload payload);
}
