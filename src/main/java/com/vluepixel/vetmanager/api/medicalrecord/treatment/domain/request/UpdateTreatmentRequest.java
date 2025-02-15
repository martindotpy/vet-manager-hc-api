package com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update treatment request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateTreatmentRequest implements Request {
    @NotNull(message = "El id es requerido")
    @Positive(message = "El id debe ser mayor a 0")
    private Long id;

    @NotBlank(message = "La descripción es requerida")
    private String description;
    @NotNull(message = "El orden es requerido")
    @Positive(message = "El orden debe ser mayor a 0")
    private Integer order;

    @NotNull(message = "El id del registro médico es requerido")
    @Positive(message = "El id del registro médico debe ser mayor a 0")
    private Long medicalRecordId;
}
