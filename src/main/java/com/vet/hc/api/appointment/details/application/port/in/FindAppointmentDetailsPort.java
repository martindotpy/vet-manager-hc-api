package com.vet.hc.api.appointment.details.application.port.in;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the appointment details.
 */
public interface FindAppointmentDetailsPort {
    /**
     * Find the appointment details by id.
     *
     * @param id the appointment details id.
     * @return the appointment details found or the failure
     */
    Result<AppointmentDetailsDto, AppointmentDetailsFailure> findById(Long id);
}
