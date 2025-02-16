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
     * @param request the register user request.
     * @return the registered user
     */
    UserDto register(RegisterUserRequest request);
}
