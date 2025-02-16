package com.vluepixel.vetmanager.api.patient.species.domain.request;

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
 * Update species request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateSpeciesRequest implements Request {
    @NotNull(message = "El id es requerido")
    @Positive(message = "El id debe ser mayor a 0")
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;
}
