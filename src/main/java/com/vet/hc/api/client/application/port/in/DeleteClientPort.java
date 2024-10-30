package com.vet.hc.api.client.application.port.in;

import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a client.
 */
public interface DeleteClientPort {
    /**
     * Deletes a client and all its data.
     *
     * @param id The id of the client to delete.
     */
    Result<Void, ClientFailure> deleteById(Long id);
}
