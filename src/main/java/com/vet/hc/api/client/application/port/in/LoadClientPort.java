package com.vet.hc.api.client.application.port.in;

import java.util.List;

import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for loading a client.
 */
public interface LoadClientPort {
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
    Result<PaginatedResponse<List<ClientDto>>, ClientFailure> match(Criteria criteria);
}
