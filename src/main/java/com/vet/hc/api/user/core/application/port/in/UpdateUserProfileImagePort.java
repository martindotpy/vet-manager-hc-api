package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.payload.UpdateUserProfileImagePayload;

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
