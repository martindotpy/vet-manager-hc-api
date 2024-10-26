package com.vet.hc.api.auth.domain.command;

import com.vet.hc.api.shared.domain.command.Command;
import com.vet.hc.api.user.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a command to register a user.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand implements Command {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
