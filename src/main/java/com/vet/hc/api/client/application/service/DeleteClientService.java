package com.vet.hc.api.client.application.service;

import com.vet.hc.api.client.application.port.in.DeleteClientPort;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to delete a client.
 */
@NoArgsConstructor
public class DeleteClientService implements DeleteClientPort {
    private ClientRepository clientRepository;

    @Inject
    public DeleteClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Result<Void, ClientFailure> delete(Long id) {
        Result<Void, ClientFailure> result = clientRepository.delete(id);

        if (result.isFailure()) {
            return Result.failure(result.getFailure());
        }

        return Result.success(null);
    }
}
