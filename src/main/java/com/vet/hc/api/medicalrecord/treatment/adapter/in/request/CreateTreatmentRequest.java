package com.vet.hc.api.medicalrecord.treatment.adapter.in.request;

import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new treatment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateTreatmentRequest implements CreateTreatmentPayload {
    @NotNull(message = "El orden es requerido")
    @Min(value = 1, message = "El orden no puede ser menor a 1")
    private Integer order;
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String description;

    @NotNull(message = "El id del registro médico es requerido")
    @Min(value = 1, message = "El id del registro médico no puede ser menor a 1")
    private Long medicalRecordId;
}
