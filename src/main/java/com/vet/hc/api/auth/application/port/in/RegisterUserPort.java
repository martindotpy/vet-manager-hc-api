package com.vet.hc.api.auth.application.port.in;

import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.EmailAlreadyInUseFailure;
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
     * @return The user that was registered
     */
    Result<UserDto, EmailAlreadyInUseFailure> register(RegisterUserCommand command);
}
