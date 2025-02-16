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
 * Create medical history request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateMedicalHistoryRequest implements Request {
    @NotBlank(message = "El contenido del historial médico es requerido")
    private String content;

    @NotNull(message = "El id del paciente es requerido")
    @Positive(message = "El id del paciente debe ser mayor a 0")
    private Long patientId;
}
