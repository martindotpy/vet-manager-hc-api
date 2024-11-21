package com.vet.hc.api.appointment.domain.payload;

/**
 * Payload to create a new appointment type.
 */
public interface CreateAppointmentTypePayload {
    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
