package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for registering a user.
 */
public interface RegisterUserPort {
    /**
     * Registers a user.
     *
     * @param payload The payload to register a user.
     * @return The user that was registered, the failure otherwise. The failure can
     *         be:
     *         <ul>
     *         <li>{@link AuthFailure#EMAIL_ALREADY_IN_USE} if the email is
     *         already
     *         registered.</li>
     *         </ul>
     */
    Result<JwtDto, AuthFailure> register(RegisterUserPayload payload);
}
