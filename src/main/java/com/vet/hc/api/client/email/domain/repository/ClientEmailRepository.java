package com.vet.hc.api.client.email.domain.repository;

import java.util.Set;

import com.vet.hc.api.client.email.domain.model.ClientEmail;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Represents a repository for client emails.
 */
public interface ClientEmailRepository {
    /**
     * Finds all client emails by client id.
     *
     * @param id The client id to search for.
     * @return The set of client emails if found, empty otherwise
     */
    Set<ClientEmail> findAllByClientId(Long id);

    /**
     * Saves a client email.
     *
     * @param clientEmail The client email to save.
     * @return The saved client email if successful, the failure otherwise. The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#DUPLICATED} if the email is already in
     *         use.</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the client email.</li>
     */
    Result<ClientEmail, RepositoryFailure> save(ClientEmail clientEmail);

    /**
     * Deletes a client email by id.
     *
     * @param id The id of the client email to delete.
     * @return The success if the client email was deleted, the failure otherwise.
     *         The failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#NOT_FOUND} if the client email was not
     *         found.</li>
     *         </ul>
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
