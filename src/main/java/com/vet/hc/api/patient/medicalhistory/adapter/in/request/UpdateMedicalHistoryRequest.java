package com.vet.hc.api.patient.medicalhistory.adapter.in.request;

import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a medical history.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateMedicalHistoryRequest implements UpdateMedicalHistoryPayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotBlank(message = "El contendio no puede estar en blanco")
    private String content;

    @NotNull(message = "El id de la cita es requerido")
    @Min(value = 1, message = "El id de la cita no puede ser menor a 1")
    private Long patientId;
}
