package com.vet.hc.api.auth.application.port.in;

import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.response.UserDto;

/**
 * Port for registering a user.
 */
public interface RegisterUserPort {
    /**
     * Registers a user.
     *
     * @param command The command to register a user.
     * @return The user that was registered, the failure otherwise. The failure can
     *         be:
     *         <ul>
     *         <li>{@link AuthFailure#EMAIL_ALREADY_IN_USE} if the email is
     *         already
     *         registered.</li>
     *         </ul>
     */
    Result<UserDto, AuthFailure> register(RegisterUserCommand command);
}
