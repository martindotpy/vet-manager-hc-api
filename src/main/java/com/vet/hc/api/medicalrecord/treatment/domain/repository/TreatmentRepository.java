package com.vet.hc.api.medicalrecord.treatment.domain.repository;

import java.util.Optional;

import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository interface for {@link Treatment} model.
 */
public interface TreatmentRepository {
    /**
     * Find a treatment by its id.
     *
     * @param id the treatment id.
     * @return an optional of medicalrecord
     */
    Optional<Treatment> findById(Long id);

    /**
     * Save a treatment.
     *
     * @param treatment record the treatment to save
     * @return Result with the saved treatment or a failure
     */
    Result<Treatment, TreatmentFailure> save(Treatment treatment);

    /**
     * Delete a treatment by its id.
     *
     * @param id the treatment id
     * @return Result with success or a failure
     */
    Result<Void, TreatmentFailure> deleteById(Long id);
}
