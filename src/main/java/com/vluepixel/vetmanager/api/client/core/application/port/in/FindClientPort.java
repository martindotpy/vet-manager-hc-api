package com.vluepixel.vetmanager.api.client.core.application.port.in;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find client port.
 */
public interface FindClientPort {
    /**
     * Find all client .
     *
     * @param criteria the paginated criteria.
     * @return the paginated client .
     */
    Paginated<ClientDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find client by id.
     *
     * @param id the id.
     * @return the client
     */
    ClientDto findById(Long id);
}
