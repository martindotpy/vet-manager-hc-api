package com.vet.hc.api.medicalrecord.treatment.adapter.in.request;

import com.vet.hc.api.medicalrecord.treatment.domain.payload.UpdateTreatmentPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a treatment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateTreatmentRequest implements UpdateTreatmentPayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotNull(message = "El orden es requerido")
    @Min(value = 1, message = "El orden no puede ser menor a 1")
    private Integer order;
    @NotNull(message = "La descripción es requerida")
    @NotEmpty(message = "La descripción no puede estar vacía")
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String description;

    @NotNull(message = "El id del registro médico es requerido")
    @Min(value = 1, message = "El id del registro médico no puede ser menor a 1")
    private Long medicalRecordId;
}
