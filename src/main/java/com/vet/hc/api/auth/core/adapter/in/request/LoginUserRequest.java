package com.vet.hc.api.auth.core.adapter.in.request;

import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Login user request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class LoginUserRequest implements LoginUserPayload {
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo es inválido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
