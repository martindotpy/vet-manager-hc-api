package com.vluepixel.vetmanager.api.appointment.details.application.port.in;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find appointment details port.
 */
public interface FindAppointmentDetailsPort {
    /**
     * Find all appointment details by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return appointment details found paginated
     */
    Paginated<AppointmentDetailsDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find appointment details by id.
     *
     * @param id the id.
     * @return appointment details found
     */
    AppointmentDetailsDto findById(Long id);
}
