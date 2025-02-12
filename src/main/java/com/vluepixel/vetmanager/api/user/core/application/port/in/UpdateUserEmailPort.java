package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.payload.UpdateUserEmailPayload;

/**
 * Update email port.
 */
public interface UpdateUserEmailPort {
    /**
     * Update email.
     *
     * @param payload the payload.
     * @return the result
     */
    UserDto update(UpdateUserEmailPayload payload);

    /**
     * Update current user.
     *
     * @param payload the payload.
     * @return the result
     */
    JwtDto updateCurrentUser(UpdateUserEmailPayload payload);
}
