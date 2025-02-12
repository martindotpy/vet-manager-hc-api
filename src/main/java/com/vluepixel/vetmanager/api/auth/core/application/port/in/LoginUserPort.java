package com.vluepixel.vetmanager.api.auth.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.domain.payload.LoginUserPayload;

/**
 * Login user port.
 */
public interface LoginUserPort {
    /**
     * Login user.
     *
     * @param payload the payload
     * @return jwt if success, failure otherwise
     */
    JwtDto login(LoginUserPayload payload);
}
