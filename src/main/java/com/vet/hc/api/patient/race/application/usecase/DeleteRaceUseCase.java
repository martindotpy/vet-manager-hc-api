package com.vet.hc.api.patient.race.application.usecase;

import com.vet.hc.api.patient.race.application.port.in.DeleteRacePort;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a race.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteRaceUseCase implements DeleteRacePort {
    private RaceRepository raceRepository;

    @Inject
    public DeleteRaceUseCase(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Result<Void, RaceFailure> deleteById(Long id) {
        log.info("Deleting race with id: {}", id);

        var result = raceRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Race  with id {} not found", id);

                return Result.failure(RaceFailure.NOT_FOUND);
            }

            log.error("Error deleting race with id {}", id, repositoryFailure);

            return Result.failure(RaceFailure.UNEXPECTED);
        }

        log.info("Race  with id {} deleted", id);

        return Result.success();
    }
}
