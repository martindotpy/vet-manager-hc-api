package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;

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
