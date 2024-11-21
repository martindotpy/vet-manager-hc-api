package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a appointment.
 */
public interface DeleteAppointmentPort {
    Result<Void, AppointmentFailure> deleteById(Long id);
}
