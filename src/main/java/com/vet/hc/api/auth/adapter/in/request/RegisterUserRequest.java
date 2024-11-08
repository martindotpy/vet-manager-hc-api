package com.vet.hc.api.auth.adapter.in.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vet.hc.api.auth.adapter.in.validation.PasswordMatchesExtendedValidation;
import com.vet.hc.api.user.domain.enums.UserRole;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@GroupSequence({ RegisterUserRequest.class, PasswordMatchesExtendedValidation.class })
public class RegisterUserRequest {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    @Size(max = 50, message = "El nombre debe tener como máximo 50 caracteres")
    private String firstName;
    @NotNull(message = "El apellido es requerido")
    @NotEmpty(message = "El apellido no puede estar vacío")
    @NotBlank(message = "El apellido no puede estar en blanco")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    @Size(max = 50, message = "El apellido debe tener como máximo 50 caracteres")
    private String lastName;
    @Email(message = "El correo debe ser válido")
    @NotNull(message = "El correo es requerido")
    @NotEmpty(message = "El correo no puede estar vacío")
    @NotBlank(message = "El correo no puede estar en blanco")
    @Size(max = 50, message = "El correo debe tener como máximo 50 caracteres")
    private String email;
    @NotNull(message = "La contraseña es requerida")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, max = 52, message = "La contraseña debe tener al menos 8 y menos de 52 caracteres")
    private String password;
    @NotNull(message = "La confirmación de la contraseña es requerida")
    @NotEmpty(message = "La confirmación de la contraseña no puede estar vacía")
    @NotBlank(message = "La confirmación de la contraseña no puede estar en blanco")
    private String confirmPassword;
    @NotNull(message = "Los roles son requeridos")
    @NotEmpty(message = "Los roles no pueden estar vacíos")
    private Set<UserRole> roles;

    /**
     * Check if the password and confirm password are mismatched.
     *
     * @return true if the password and confirm password are mismatched, false
     */
    @JsonIgnore
    @AssertFalse(message = "La contraseña y su confirmación no coinciden", groups = PasswordMatchesExtendedValidation.class)
    public boolean isPasswordMismatch() {
        return password != null && !password.equals(confirmPassword);
    }
}
