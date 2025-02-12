package com.vluepixel.vetmanager.api.auth.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.domain.payload.RegisterUserPayload;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

/**
 * Register user port.
 */
public interface RegisterUserPort {
    /**
     * Register user.
     *
     * @param payload the payload
     * @return the jwt if success, failure otherwise
     */
    UserDto register(RegisterUserPayload payload);
}
