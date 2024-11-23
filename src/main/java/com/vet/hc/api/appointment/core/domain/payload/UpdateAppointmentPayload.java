package com.vet.hc.api.appointment.core.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update appointment payload.
 */
public interface UpdateAppointmentPayload extends Payload {
    Long getId();

    String getDescription();

    LocalDateTime getStartAt();

    Long getPatientId();
}
