package com.vet.hc.api.client.core.application.usecase;

import com.vet.hc.api.client.core.application.port.in.DeleteClientPort;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to delete a client.
 */
@NoArgsConstructor
public class DeleteClientUseCase implements DeleteClientPort {
    private ClientRepository clientRepository;

    @Inject
    public DeleteClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Result<Void, ClientFailure> deleteById(Long id) {
        Result<Void, ClientFailure> result = clientRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(result.getFailure());
        }

        return Result.success();
    }
}
