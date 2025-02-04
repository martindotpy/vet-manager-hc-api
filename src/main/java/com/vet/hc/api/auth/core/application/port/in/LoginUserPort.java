package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.domain.result.Result;

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
    Result<JwtDto, AuthFailure> login(LoginUserPayload payload);
}
