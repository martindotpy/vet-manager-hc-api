package com.vluepixel.vetmanager.api.user.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserEmailRequest;

/**
 * Update email port.
 */
public interface UpdateUserEmailPort {
    /**
     * Update email.
     *
     * @param request the request.
     * @return the result
     */
    UserDto update(UpdateUserEmailRequest request);

    /**
     * Update current user.
     *
     * @param request the request.
     * @return the result
     */
    JwtDto updateCurrentUser(UpdateUserEmailRequest request);
}
