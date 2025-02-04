package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;

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
    Result<UserDto, AuthFailure> register(RegisterUserPayload payload);
}
