package com.vluepixel.vetmanager.api.appointment.type.application.port.in;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find appointment type port.
 */
public interface FindAppointmentTypePort {
    /**
     * Find all appointment types.
     *
     * @param criteria the paginated criteria.
     * @return the paginated appointment types.
     */
    Paginated<AppointmentTypeDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find appointment type by id.
     *
     * @param id the id.
     * @return the appointment type
     */
    AppointmentTypeDto findById(Long id);
}
