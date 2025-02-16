package com.vluepixel.vetmanager.api.patient.race.application.port.in;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find race port.
 */
public interface FindRacePort {
    /**
     * Find all race by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return races found paginated
     */
    Paginated<RaceDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find race by id.
     *
     * @param id the id.
     * @return race found
     */
    RaceDto findById(Integer id);
}
