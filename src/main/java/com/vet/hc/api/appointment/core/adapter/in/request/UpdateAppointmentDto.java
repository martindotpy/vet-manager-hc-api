package com.vet.hc.api.appointment.core.adapter.in.request;

import java.time.LocalDateTime;

import com.vet.hc.api.appointment.core.domain.payload.UpdateAppointmentPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update appointment dto (request).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAppointmentDto implements UpdateAppointmentPayload {
    @NotNull(message = "La descripción no puede ser nula")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;
    @NotNull(message = "La descripción no puede ser nula")
    @NotEmpty(message = "La descripción no puede estar vacía")
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String description;
    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDateTime startAt;
    // @NotNull(message = "El id del paciente no puede ser nulo")
    // @Min(value = 1, message = "El id del paciente no puede ser menor a 1")
    private Long patientId;
}
