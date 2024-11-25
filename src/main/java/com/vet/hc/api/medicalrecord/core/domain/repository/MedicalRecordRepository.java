package com.vet.hc.api.medicalrecord.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link MedicalRecord} model.
 */
public interface MedicalRecordRepository {
    /**
     * Find a medical record by its id.
     *
     * @param id the medical record id.
     * @return an optional of medicalrecord
     */
    Optional<MedicalRecord> findById(Long id);

    /**
     * Save a medical record.
     *
     * @param medicalRecord record the medical record to save
     * @return Result with the saved medical record or a failure
     */
    Result<MedicalRecord, RepositoryFailure> save(MedicalRecord medicalRecord);

    /**
     * Delete a medical record by its id.
     *
     * @param id the medical record id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
