package com.vet.hc.api.client.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository for clients.
 */
public interface ClientRepository {
    /**
     * Find all clients.
     *
     * @return The list of clients.
     */
    List<Client> findAll();

    /**
     * Find a client by id.
     *
     * @param id The id of the client to find.
     * @return The client with the given id.
     */
    Optional<Client> findById(Long id);

    /**
     * Create a new client.
     *
     * @param client The client to create.
     * @return The created client
     */
    Client save(Client client);

    /**
     * Find clients that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of clients that match the criteria
     */
    PaginatedResponse<List<Client>> match(Criteria criteria);

    /**
     * Delete a client.
     *
     * @param id The id of the client to delete.
     * @return The result of the operation
     */
    Result<Void, ClientFailure> deleteById(Long id);
}
