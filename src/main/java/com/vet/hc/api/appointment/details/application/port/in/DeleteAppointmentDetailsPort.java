package com.vet.hc.api.appointment.details.application.port.in;

import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting an appointment details by id.
 */
public interface DeleteAppointmentDetailsPort {
    /**
     * Delete an appointment details by id.
     *
     * @param id Id of the appointment details to delete.
     * @return Result with the success or the failure
     */
    Result<Void, AppointmentDetailsFailure> deleteById(Long id);
}
