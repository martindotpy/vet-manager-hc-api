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
 * Request for updating an appointment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAppointmentRequest implements Request {
    @NotNull(message = "El id del tipo de cita no puede estar vacío")
    @Positive(message = "El id del tipo de cita debe ser mayor a 0")
    private Long id;

    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDateTime startAt;
    private String description;

    @NotEmpty(message = "La lista de detalles no puede estar vacía")
    private List<@NotNull(message = "Ningún detalle puede ser nulo") UpdateAppointmentDetailsRequest> details;
    private Long patientId;
}
