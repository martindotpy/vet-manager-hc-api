package com.vet.hc.api.appointment.domain.payload;

import java.util.List;

import com.vet.hc.api.appointment.domain.enums.AppointmentType;

/**
 * Update appointment payload.
 */
public interface UpdateAppointmentPayload {
    Long getId();

    String getDescription();

    List<AppointmentType> getTypes();

    Long getPatientId();
}
