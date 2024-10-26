package com.vet.hc.api.auth.application.port.in;

import com.vet.hc.api.auth.domain.command.LoginUserCommand;
import com.vet.hc.api.auth.domain.failure.InvalidCredentials;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.response.UserDto;

/**
 * Port for logging in a user.
 */
public interface LoginUserPort {
    /**
     * Logs in a user.
     *
     * @param command The command to login a user.
     * @return The user that was logged in
     */
    Result<UserDto, InvalidCredentials> login(LoginUserCommand command);
}
