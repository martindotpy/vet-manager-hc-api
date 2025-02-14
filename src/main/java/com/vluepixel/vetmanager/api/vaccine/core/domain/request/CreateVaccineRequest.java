package com.vluepixel.vetmanager.api.vaccine.core.domain.request;

import java.time.LocalDateTime;

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
 * Create vaccine request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateVaccineRequest implements Request {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;
    @NotNull(message = "La dosis en mililitros no puede ser nula")
    @Positive(message = "La dosis en mililitros debe ser mayor a 0")
    private Integer doseInMilliliters;
    @NotNull(message = "La fecha de aplicación no puede ser nula")
    private LocalDateTime providedAt;

    @NotNull(message = "El id del paciente no puede ser nulo")
    @Positive(message = "El id del paciente debe ser mayor a 0")
    private Long patientId;
    @NotNull(message = "El id del vacunador no puede ser nulo")
    @Positive(message = "El id del vacunador debe ser mayor a 0")
    private Long vaccinatorId;
    // TODO: Product sale
}
