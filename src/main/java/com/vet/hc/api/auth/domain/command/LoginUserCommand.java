package com.vet.hc.api.auth.domain.command;

import com.vet.hc.api.shared.domain.payload.Payload;

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
public class LoginUserCommand implements Payload {
    private String email;
    private String password;
}
