package com.vet.hc.api.appointment.type.domain.payload;

/**
 * Payload for updating an appointment type.
 */
public interface UpdateAppointmentTypePayload {
    Long getId();

    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
