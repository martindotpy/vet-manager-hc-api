package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
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
    UserDto register(RegisterUserPayload payload);
}
