package com.vet.hc.api.auth.domain.command;

import com.vet.hc.api.shared.domain.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a command to login a user.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserCommand implements Command {
    private String email;
    private String password;
}
