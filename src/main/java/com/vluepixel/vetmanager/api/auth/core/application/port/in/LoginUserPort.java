package com.vluepixel.vetmanager.api.auth.core.application.port.in;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.domain.request.LoginUserRequest;

/**
 * Login user port.
 */
public interface LoginUserPort {
    /**
     * Login user.
     *
     * @param request the request
     * @return jwt if success, failure otherwise
     */
    JwtDto login(LoginUserRequest request);
}
