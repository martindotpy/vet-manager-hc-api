package com.vet.hc.api.client.application.service;

import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.application.port.in.UpdateClientPort;
import com.vet.hc.api.client.domain.command.UpdateFullDataClientCommand;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Service for updating a client.
 */
public class UpdateClientService implements UpdateClientPort {
    @Override
    public Result<FullDataClientDto, ClientFailure> update(Long id, UpdateFullDataClientCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
