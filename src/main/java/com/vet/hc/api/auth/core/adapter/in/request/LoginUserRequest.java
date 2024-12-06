package com.vet.hc.api.auth.core.adapter.in.request;

import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for logging in a user.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class LoginUserRequest implements LoginUserPayload {
    @NotNull(message = "El correo es requerido")
    @NotEmpty(message = "El correo no puede estar vacío")
    @NotBlank(message = "El correo no puede estar en blanco")
    @Email(message = "El correo debe ser válido")
    private String email;
    @NotNull(message = "La contraseña es requerida")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;
}
