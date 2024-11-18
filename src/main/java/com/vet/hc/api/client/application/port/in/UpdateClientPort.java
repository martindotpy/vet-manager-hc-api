package com.vet.hc.api.client.application.port.in;

import com.vet.hc.api.client.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.payload.UpdateFullDataClientPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a client.
 */
public interface UpdateClientPort {
    /**
     * Updates a client.
     *
     * @param command The command to update a client.
     * @return The updated client
     */
    Result<FullDataClientDto, ClientFailure> update(UpdateFullDataClientPayload command);
}
