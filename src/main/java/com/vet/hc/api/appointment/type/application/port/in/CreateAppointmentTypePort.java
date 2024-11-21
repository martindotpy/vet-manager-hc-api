package com.vet.hc.api.appointment.type.application.port.in;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.payload.CreateAppointmentTypePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create an appointment type.
 */
public interface CreateAppointmentTypePort {
    /**
     * Create an appointment type.
     *
     * @param payload Payload with the data to create the appointment type.
     * @return Result with the appointment type created or the failure
     */
    Result<AppointmentTypeDto, AppointmentTypeFailure> create(CreateAppointmentTypePayload payload);
}
