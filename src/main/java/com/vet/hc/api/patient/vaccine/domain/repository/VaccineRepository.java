package com.vet.hc.api.patient.vaccine.domain.repository;

import java.util.Optional;

import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository interface for {@link Vaccine} model.
 */
public interface VaccineRepository {
    /**
     * Find a vaccine by its id.
     *
     * @param id the vaccine id.
     * @return an optional of vaccine
     */
    Optional<Vaccine> findById(Long id);

    /**
     * Save a vaccine.
     *
     * @param vaccine the vaccine to save
     * @return Result with the saved vaccine or a failure
     */
    Result<Vaccine, VaccineFailure> save(Vaccine vaccine);

    /**
     * Delete a vaccine by its id.
     *
     * @param id the vaccine id
     * @return Result with success or a failure
     */
    Result<Void, VaccineFailure> deleteById(Long id);
}
