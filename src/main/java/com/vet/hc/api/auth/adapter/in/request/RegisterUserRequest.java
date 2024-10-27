package com.vet.hc.api.auth.adapter.in.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for registering a new user.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String firstName;
    @NotNull(message = "El apellido es requerido")
    @NotEmpty(message = "El apellido no puede estar vacío")
    @NotBlank(message = "El apellido no puede estar en blanco")
    private String lastName;
    @Email(message = "El correo debe ser válido")
    @NotNull(message = "El correo es requerido")
    @NotEmpty(message = "El correo no puede estar vacío")
    @NotBlank(message = "El correo no puede estar en blanco")
    private String email;
    @NotNull(message = "La contraseña es requerido")
    @NotEmpty(message = "La contraseña no puede estar vacío")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Length(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
