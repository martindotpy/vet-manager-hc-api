package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.payload.UpdateAppointmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a appointment.
 */
public interface UpdateAppointmentPort {
    /**
     * Update a appointment.
     *
     * @param payload the payload to update a appointment.
     * @return the updated appointment
     */
    Result<AppointmentDto, AppointmentFailure> update(UpdateAppointmentPayload payload);
}
