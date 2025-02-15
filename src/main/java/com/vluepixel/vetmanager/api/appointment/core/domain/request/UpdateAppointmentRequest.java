package com.vluepixel.vetmanager.api.appointment.core.domain.request;

import java.time.LocalDateTime;
import java.util.List;

import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update appointment request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAppointmentRequest implements Request {
    @NotNull(message = "El id del tipo de cita es requerido")
    @Positive(message = "El id del tipo de cita debe ser mayor a 0")
    private Long id;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDateTime startAt;
    private String description;

    @NotEmpty(message = "La lista de detalles es requerido")
    private List<@NotNull(message = "Ningún detalle puede ser nulo") UpdateAppointmentDetailsRequest> details;
    private Long patientId;
}
