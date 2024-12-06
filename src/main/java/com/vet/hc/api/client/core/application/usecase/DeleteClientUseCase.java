package com.vet.hc.api.client.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.client.core.application.port.in.DeleteClientPort;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Service to delete a client.
 */
@UseCase
@RequiredArgsConstructor
public final class DeleteClientUseCase implements DeleteClientPort {
    private final ClientRepository clientRepository;

    @Override
    public Result<Void, ClientFailure> deleteById(Long id) {
        Result<Void, ClientFailure> result = clientRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(result.getFailure());
        }

        return Result.success();
    }
}
