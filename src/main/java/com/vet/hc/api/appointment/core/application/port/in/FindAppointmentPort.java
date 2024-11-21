package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.query.PaginatedAppointment;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding a appointment.
 */
public interface FindAppointmentPort {
    /**
     * Find a appointment by its id.
     *
     * @param id The id of the appointment.
     * @return The appointment if found, otherwise a failure.
     */
    Result<AppointmentDto, AppointmentFailure> findById(Long id);

    /**
     * Find all matching appointments.
     *
     * @param criteria The criteria to filter the appointments.
     * @return The matching appointments
     */
    Result<PaginatedAppointment, AppointmentFailure> match(Criteria criteria);
}
