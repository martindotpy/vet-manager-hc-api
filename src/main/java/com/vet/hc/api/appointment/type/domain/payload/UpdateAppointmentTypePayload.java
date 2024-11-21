package com.vet.hc.api.appointment.type.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating an appointment type.
 */
public interface UpdateAppointmentTypePayload extends Payload {
    Long getId();

    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
