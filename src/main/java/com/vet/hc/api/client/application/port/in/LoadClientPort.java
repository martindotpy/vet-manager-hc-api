package com.vet.hc.api.client.application.port.in;

import java.util.List;

import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for loading a client.
 */
public interface LoadClientPort {
    Result<FullDataClientDto, ClientFailure> findById(Long id);

    default Result<List<ClientDto>, ClientFailure> match() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
