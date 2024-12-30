package com.vet.hc.api.patient.species.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.species.application.port.in.DeleteSpeciesPort;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a species.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteSpeciesUseCase implements DeleteSpeciesPort {
    private final SpeciesRepository speciesRepository;

    @Override
    public Result<Void, SpeciesFailure> deleteById(Long id) {
        log.info("Deleting species with id: {}", id);

        var result = speciesRepository.deleteById(id);

        if (result.isFailure()) {
            log.error("Error deleting species with id {}", id);

            return Result.failure(SpeciesFailure.UNEXPECTED);
        }

        log.info("Species  with id {} deleted", id);

        return Result.success();
    }
}
