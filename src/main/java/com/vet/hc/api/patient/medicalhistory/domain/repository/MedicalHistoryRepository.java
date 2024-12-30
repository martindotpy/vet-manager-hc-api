package com.vet.hc.api.patient.medicalhistory.domain.repository;

import java.util.Optional;

import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository interface for {@link MedicalHistory} model.
 */
public interface MedicalHistoryRepository {
    /**
     * Find a medical history by its id.
     *
     * @param id the medical history id.
     * @return an optional of medicalhistory
     */
    Optional<MedicalHistory> findById(Long id);

    /**
     * Save a medical history.
     *
     * @param medical history the medical history to save
     * @return Result with the saved medical history or a failure
     */
    Result<MedicalHistory, MedicalHistoryFailure> save(MedicalHistory medicalHistory);

    /**
     * Delete a medical history by its id.
     *
     * @param id the medical history id
     * @return Result with success or a failure
     */
    Result<Void, MedicalHistoryFailure> deleteById(Long id);
}
