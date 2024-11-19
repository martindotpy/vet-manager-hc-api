package com.vet.hc.api.auth.application.port.in;

import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.auth.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.domain.dto.UserDto;

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
    Result<UserDto, AuthFailure> login(LoginUserPayload payload);
}
