package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserEmailRequest;

/**
 * Update email port.
 */
public interface UpdateUserEmailPort {
    /**
     * Update a user email.
     *
     * @param request the update user email request.
     * @return the updated user
     */
    UserDto update(UpdateUserEmailRequest request);

    /**
     * Update the current user email.
     *
     * @param request the update user email request.
     * @return the updated user as JWT
     */
    JwtDto updateCurrentUser(UpdateUserEmailRequest request);
}
