package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add details to an appointment.
 */
public interface AddDetailsToAppointmentPort {
    /**
     * Add details to an appointment.
     *
     * @param payload The details to add to the appointment.
     * @return The result of the operation
     */
    Result<AppointmentDto, AppointmentFailure> add(CreateAppointmentDetailsPayload payload);
}
