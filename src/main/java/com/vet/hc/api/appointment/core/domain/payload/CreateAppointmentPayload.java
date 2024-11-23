package com.vet.hc.api.appointment.core.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Create appointment payload.
 */
public interface CreateAppointmentPayload extends Payload {
    String getDescription();

    LocalDateTime getStartAt();

    Long getPatientId();
}
