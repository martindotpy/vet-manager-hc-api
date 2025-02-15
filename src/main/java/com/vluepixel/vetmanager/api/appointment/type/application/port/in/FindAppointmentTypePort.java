package com.vluepixel.vetmanager.api.appointment.type.application.port.in;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find appointment type port.
 */
public interface FindAppointmentTypePort {
    /**
     * Find all appointment types by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return appointment types found paginated
     */
    Paginated<AppointmentTypeDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find appointment type by id.
     *
     * @param id the id.
     * @return appointment type found
     */
    AppointmentTypeDto findById(Integer id);
}
