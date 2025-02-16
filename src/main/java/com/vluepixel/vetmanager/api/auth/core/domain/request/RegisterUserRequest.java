package com.vluepixel.vetmanager.api.auth.core.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

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
public final class RegisterUserRequest implements Request {
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no debe de tener más de 50 caracteres")
    private String firstName;
    @NotBlank(message = "El apellido es requerido")
    @Size(max = 50, message = "El apellido no debe de tener más de 50 caracteres")
    private String lastName;
    @Email(message = "El correo es inválido")
    @NotBlank(message = "El correo es requerido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    // Prevent the password from being too long cause it will break bcrypt
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    private String password;
}
