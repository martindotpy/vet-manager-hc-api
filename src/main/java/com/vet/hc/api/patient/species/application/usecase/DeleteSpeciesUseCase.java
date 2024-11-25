package com.vet.hc.api.patient.species.application.usecase;

import com.vet.hc.api.patient.species.application.port.in.DeleteSpeciesPort;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a species.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteSpeciesUseCase implements DeleteSpeciesPort {
    private SpeciesRepository speciesRepository;

    @Inject
    public DeleteSpeciesUseCase(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public Result<Void, SpeciesFailure> deleteById(Long id) {
        log.info("Deleting species with id: {}", id);

        var result = speciesRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Species  with id {} not found", id);

                return Result.failure(SpeciesFailure.NOT_FOUND);
            }

            log.error("Error deleting species with id {}", id, repositoryFailure);

            return Result.failure(SpeciesFailure.UNEXPECTED);
        }

        log.info("Species  with id {} deleted", id);

        return Result.success();
    }
}