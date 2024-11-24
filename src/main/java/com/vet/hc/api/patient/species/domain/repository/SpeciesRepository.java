package com.vet.hc.api.patient.species.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link Species} model.
 */
public interface SpeciesRepository {
    /**
     * Find all species.
     *
     * @return a list of species
     */
    List<Species> findAll();

    /**
     * Find a species by its id.
     *
     * @param id the species id.
     * @return a optional of species
     */
    Optional<Species> findById(Long id);

    /**
     * Save a species.
     *
     * @param species the species to save
     * @return Result with the saved species or a failure
     */
    Result<Species, RepositoryFailure> save(Species species);

    /**
     * Delete a species by its id.
     *
     * @param id the species id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
