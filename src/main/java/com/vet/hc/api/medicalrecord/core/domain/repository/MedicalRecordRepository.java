package com.vet.hc.api.medicalrecord.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.shared.domain.query.Result;

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
    Result<MedicalRecord, MedicalRecordFailure> save(MedicalRecord medicalRecord);

    /**
     * Delete a medical record by its id.
     *
     * @param id the medical record id
     * @return Result with success or a failure
     */
    Result<Void, MedicalRecordFailure> deleteById(Long id);
}
