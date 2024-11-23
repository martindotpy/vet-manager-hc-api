package com.vet.hc.api.appointment.details.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new appointment details.
 */
public interface CreateAppointmentDetailsPayload extends Payload {
    Integer getDurationInMinutes();

    Double getPrice();

    Long getAppointmentTypeId();

    Long getAppointmentId();
}
