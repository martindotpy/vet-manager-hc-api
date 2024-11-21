package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.payload.CreateAppointmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a appointment.
 */
public interface CreateAppointmentPort {
    Result<AppointmentDto, AppointmentFailure> create(CreateAppointmentPayload payload);
}
