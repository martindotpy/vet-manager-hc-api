package com.vet.hc.api.client.domain.repository;

import java.util.Optional;

import com.vet.hc.api.client.domain.model.FullDataClient;

/**
 * Repository for full data of clients.
 */
public interface FullDataClientRepository {
    /**
     * Find full data of a client by its id.
     *
     * @param id The client id.
     * @return The full data of the client
     */
    Optional<FullDataClient> findByClientId(Long id);
}
