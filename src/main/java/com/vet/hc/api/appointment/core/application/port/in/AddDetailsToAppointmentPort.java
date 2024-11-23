package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

public interface AddDetailsToAppointmentPort {
    Result<AppointmentDto, AppointmentFailure> add(CreateAppointmentDetailsPayload payload);
}
