package com.vet.hc.api.appointment.details.application.port.in;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.payload.UpdateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating an appointment details.
 */
public interface UpdateAppointmentDetailsPort {
    /**
     * Update an appointment details.
     *
     * @param payload Payload with the data to update the appointment details.
     * @return Result with the appointment details updated or the failure
     */
    Result<AppointmentDetailsDto, AppointmentDetailsFailure> update(UpdateAppointmentDetailsPayload payload);
}
