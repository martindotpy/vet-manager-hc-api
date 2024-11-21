package com.vet.hc.api.appointment.domain.payload;

import java.util.List;

import com.vet.hc.api.appointment.domain.model.AppointmentType;

/**
 * Update appointment payload.
 */
public interface UpdateAppointmentPayload {
    Long getId();

    String getDescription();

    List<AppointmentType> getTypes();

    Long getPatientId();
}
