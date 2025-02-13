package com.vluepixel.vetmanager.api.auth.core.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update password request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdatePasswordRequest implements Request {
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    private String password;
    @NotBlank(message = "La nueva contraseña es requerida")
    @Size(min = 8, max = 60, message = "La nueva contraseña debe tener entre 8 y 60 caracteres")
    private String newPassword;
}
