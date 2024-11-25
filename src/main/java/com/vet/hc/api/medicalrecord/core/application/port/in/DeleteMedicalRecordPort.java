package com.vet.hc.api.medicalrecord.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a medical record by id.
 */
public interface DeleteMedicalRecordPort {
    /**
     * Delete a medical record by id.
     *
     * @param id Id of the medical record to delete.
     * @return Result with the success or the failure
     */
    Result<Void, MedicalRecordFailure> deleteById(Long id);
}
