package com.vet.hc.api.appointment.type.application.port.in;

import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting an appointment type by id.
 */
public interface DeleteAppointmentTypePort {
    /**
     * Delete an appointment type by id.
     *
     * @param id Id of the appointment type to delete.
     * @return Result with the success or the failure
     */
    Result<Void, AppointmentTypeFailure> deleteById(Long id);
}
