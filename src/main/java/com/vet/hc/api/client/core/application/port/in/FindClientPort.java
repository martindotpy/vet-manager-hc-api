package com.vet.hc.api.client.core.application.port.in;

import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding a client.
 */
public interface FindClientPort {
    /**
     * Find all clients.
     *
     * @param id The id of the client to find.
     * @return The client with the given id
     */
    Result<FullDataClientDto, ClientFailure> findById(Long id);

    /**
     * Find clients that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of clients that match the criteria
     */
    Result<Paginated<ClientDto>, ClientFailure> match(Criteria criteria);
}
