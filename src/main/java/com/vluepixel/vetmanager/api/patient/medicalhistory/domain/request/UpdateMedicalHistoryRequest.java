package com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update medical history request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateMedicalHistoryRequest implements Request {
    @NotNull(message = "El id no puede estar vacío")
    @Positive(message = "El id debe ser un número positivo")
    private Integer id;

    @NotBlank(message = "El contenido del historial médico no puede estar vacío")
    private String content;

    @NotNull(message = "El id del paciente no puede ser nulo")
    @Positive(message = "El id del paciente debe ser mayor a 0")
    private Integer patientId;
}
