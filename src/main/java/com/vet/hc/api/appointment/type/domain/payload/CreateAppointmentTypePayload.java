package com.vet.hc.api.appointment.type.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new appointment type.
 */
public interface CreateAppointmentTypePayload extends Payload {
    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
