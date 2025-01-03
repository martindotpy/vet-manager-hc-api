package com.vet.hc.api.client.phone.domain.repository;

import java.util.Set;

import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;

/**
 * Represents a repository for client phones.
 */
public interface ClientPhoneRepository {
    /**
     * Finds all client phones by client id.
     *
     * @param id The client id to search for.
     * @return The set of client phones if found, empty otherwise
     */
    Set<ClientPhone> findAllByClientId(Long id);

    /**
     * Saves a client phone.
     *
     * @param clientPhone The client phone to save.
     * @return The saved client phone if successful, the failure otherwise. The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailureType#DUPLICATED} if the phone is already
     *         in
     *         use.</li>
     *         <li>{@link RepositoryFailureType#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the client phone.</li>
     */
    Result<ClientPhone, ClientFailure> save(ClientPhone clientPhone);

    /**
     * Deletes a client phone by id.
     *
     * @param id The id of the client phone to delete.
     * @return The success if the client phone was deleted, the failure otherwise.
     *         The failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailureType#NOT_FOUND} if the client phone was
     *         not
     *         found.</li>
     *         </ul>
     */
    Result<Void, ClientFailure> deleteById(Long id);
}
