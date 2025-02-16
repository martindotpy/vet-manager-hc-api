package com.vluepixel.vetmanager.api.user.core.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update user request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateUserRequest implements Request {
    @NotNull(message = "El id es requerido")
    @Positive(message = "El id debe ser mayor a 0")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre es muy largo")
    private String firstName;
    @NotBlank(message = "El apellido es requerido")
    @Size(max = 50, message = "El apellido es muy largo")
    private String lastName;
}
