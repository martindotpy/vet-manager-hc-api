package com.vet.hc.api.auth.adapter.in.request;

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
public class LoginUserRequest {
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
