package com.vluepixel.vetmanager.api.appointment.core.application.port.in;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find appointment port.
 */
public interface FindAppointmentPort {
    /**
     * Find all appointment .
     *
     * @param criteria the paginated criteria.
     * @return the paginated appointment .
     */
    Paginated<AppointmentDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find appointment by id.
     *
     * @param id the id.
     * @return the appointment
     */
    AppointmentDto findById(Long id);
}
