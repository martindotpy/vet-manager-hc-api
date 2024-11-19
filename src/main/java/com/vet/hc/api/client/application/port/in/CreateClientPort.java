package com.vet.hc.api.client.application.port.in;

import com.vet.hc.api.client.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.payload.CreateClientPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a new client.
 */
public interface CreateClientPort {
    /**
     * Creates a new client.
     *
     * @param payload The payload to create a new client.
     * @return The created client
     */
    Result<FullDataClientDto, ClientFailure> create(CreateClientPayload payload);
}
