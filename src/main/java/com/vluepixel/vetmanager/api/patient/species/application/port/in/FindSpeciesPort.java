package com.vluepixel.vetmanager.api.patient.species.application.port.in;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find species port.
 */
public interface FindSpeciesPort {
    /**
     * Find all species by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return species found paginated
     */
    Paginated<SpeciesDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find species by id.
     *
     * @param id the id.
     * @return species found
     */
    SpeciesDto findById(Integer id);
}
