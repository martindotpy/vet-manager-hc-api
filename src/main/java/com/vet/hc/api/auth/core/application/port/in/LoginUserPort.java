package com.vet.hc.api.auth.core.application.port.in;

import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for logging in a user.
 */
public interface LoginUserPort {
    /**
     * Logs in a user.
     *
     * @param payload The payload to login a user.
     * @return The user that was logged in, the failure otherwise. The failure can
     *         be:
     *         <ul>
     *         <li>{@link AuthFailure#INVALID_CREDENTIALS} if the credentials are
     *         invalid.</li>
     *         </ul>
     */
    Result<JwtDto, AuthFailure> login(LoginUserPayload payload);
}
