package com.vet.hc.api.auth.core.adapter.in.request;

import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Register user request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RegisterUserRequest implements RegisterUserPayload {
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name is too long")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name is too long")
    private String lastName;
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    // Prevent the password from being too long cause it will break bcrypt
    @Size(min = 8, max = 60, message = "Password must be between 8 and 64 characters")
    private String password;
}
