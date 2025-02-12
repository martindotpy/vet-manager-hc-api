package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.payload.UpdateUserPayload;

/**
 * Update user port.
 */
public interface UpdateUserPort {
    /**
     * Update a user.
     *
     * @param payload the payload.
     * @return the result.
     */
    UserDto update(UpdateUserPayload payload);

    /**
     * Update the current user.
     *
     * @param payload the payload.
     * @return the result
     */
    JwtDto updateCurrentUser(UpdateUserPayload payload);
}
