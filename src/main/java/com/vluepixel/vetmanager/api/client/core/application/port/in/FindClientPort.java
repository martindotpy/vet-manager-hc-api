package com.vluepixel.vetmanager.api.client.core.application.port.in;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find client port.
 */
public interface FindClientPort {
    /**
     * Find all client by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return clients found paginated
     */
    Paginated<ClientDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find client by id.
     *
     * @param id the id.
     * @return client found
     */
    ClientDto findById(Long id);
}
