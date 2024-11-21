package com.vet.hc.api.client.core.application.port.in;

import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.payload.UpdateFullDataClientPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a client.
 */
public interface UpdateClientPort {
    /**
     * Updates a client.
     *
     * @param payload The payload to update a client.
     * @return The updated client
     */
    Result<FullDataClientDto, ClientFailure> update(UpdateFullDataClientPayload payload);
}
