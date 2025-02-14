package com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request;

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
 * Create medical history request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateMedicalHistoryRequest implements Request {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = "El contenido del historial médico no puede estar vacío")
    private String content;

    @NotNull(message = "El id del paciente no puede ser nulo")
    @Positive(message = "El id del paciente debe ser mayor a 0")
    private Integer patientId;
}
