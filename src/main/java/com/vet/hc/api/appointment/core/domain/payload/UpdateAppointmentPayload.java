package com.vet.hc.api.appointment.core.domain.payload;

import java.util.List;

import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

/**
 * Update appointment payload.
 */
public interface UpdateAppointmentPayload {
    Long getId();

    String getDescription();

    List<AppointmentType> getTypes();

    Long getPatientId();
}
