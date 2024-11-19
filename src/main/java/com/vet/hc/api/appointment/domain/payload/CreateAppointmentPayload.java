package com.vet.hc.api.appointment.domain.payload;

import java.util.List;

import com.vet.hc.api.appointment.domain.enums.AppointmentType;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Create appointment payload.
 */
public interface CreateAppointmentPayload extends Payload {
    String getDescription();

    List<AppointmentType> getTypes();

    Long getPatientId();
}
