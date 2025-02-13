package com.vluepixel.vetmanager.api.appointment.core.domain.request;

import java.time.LocalDateTime;
import java.util.List;

import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request for creating an appointment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAppointmentRequest implements Request {
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDateTime startAt;
    private String description;

    @NotEmpty(message = "La lista de detalles no puede estar vacía")
    private List<@NotNull(message = "Ningún detalle puede ser nulo") CreateAppointmentDetailsRequest> details;
    private Long patientId;
}
