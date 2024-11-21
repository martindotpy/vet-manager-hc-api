package com.vet.hc.api.appointment.type.application.port.in;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.payload.UpdateAppointmentTypePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating an appointment type.
 */
public interface UpdateAppointmentTypePort {
    /**
     * Update an appointment type.
     *
     * @param payload Payload with the data to update the appointment type.
     * @return Result with the appointment type updated or the failure
     */
    Result<AppointmentTypeDto, AppointmentTypeFailure> update(UpdateAppointmentTypePayload payload);
}
