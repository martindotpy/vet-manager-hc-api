package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;

/**
 * Update user port.
 */
public interface UpdateUserPort {
    /**
     * Update a user info.
     *
     * @param request the request.
     * @return the updated user
     */
    UserDto update(UpdateUserRequest request);

    /**
     * Update the current user info.
     *
     * @param request the request.
     * @return the updated user as JWT
     */
    JwtDto updateCurrentUser(UpdateUserRequest request);
}
