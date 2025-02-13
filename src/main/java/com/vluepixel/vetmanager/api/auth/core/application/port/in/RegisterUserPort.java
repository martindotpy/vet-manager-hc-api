package com.vluepixel.vetmanager.api.auth.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.domain.request.RegisterUserRequest;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

/**
 * Register user port.
 */
public interface RegisterUserPort {
    /**
     * Register user.
     *
     * @param request the request
     * @return the jwt if success, failure otherwise
     */
    UserDto register(RegisterUserRequest request);
}
