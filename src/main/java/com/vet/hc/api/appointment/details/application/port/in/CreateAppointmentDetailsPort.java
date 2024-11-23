package com.vet.hc.api.appointment.details.application.port.in;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create an appointment details.
 */
public interface CreateAppointmentDetailsPort {
    /**
     * Create an appointment details.
     *
     * @param payload Payload with the data to create the appointment details.
     * @return Result with the appointment details created or the failure
     */
    Result<AppointmentDetailsDto, AppointmentDetailsFailure> create(CreateAppointmentDetailsPayload payload);
}
