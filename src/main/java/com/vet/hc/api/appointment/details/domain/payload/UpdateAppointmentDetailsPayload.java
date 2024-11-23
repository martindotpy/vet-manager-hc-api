package com.vet.hc.api.appointment.details.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating an appointment details.
 */
public interface UpdateAppointmentDetailsPayload extends Payload {
    Long getId();

    Integer getDurationInMinutes();

    Double getPrice();

    Long getAppointmentTypeId();
}
