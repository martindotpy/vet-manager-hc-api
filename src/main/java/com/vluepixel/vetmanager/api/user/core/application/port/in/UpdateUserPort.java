package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;

/**
 * Update user port.
 */
public interface UpdateUserPort {
    /**
     * Update a user.
     *
     * @param request the request.
     * @return the result.
     */
    UserDto update(UpdateUserRequest request);

    /**
     * Update the current user.
     *
     * @param request the request.
     * @return the result
     */
    JwtDto updateCurrentUser(UpdateUserRequest request);
}
