package com.vet.hc.api.patient.race.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link Race} model.
 */
public interface RaceRepository {
    /**
     * Find all races.
     *
     * @return a list of races
     */
    List<Race> findAll();

    /**
     * Find a race by its id.
     *
     * @param id the race id.
     * @return a optional of race
     */
    Optional<Race> findById(Long id);

    /**
     * Save a race.
     *
     * @param race the race to save
     * @return Result with the saved race or a failure
     */
    Result<Race, RepositoryFailure> save(Race race);

    /**
     * Delete a race by its id.
     *
     * @param id the race id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
