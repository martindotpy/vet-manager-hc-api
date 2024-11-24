package com.vet.hc.api.patient.medicalhistory.application.port.in;

import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a medical history by id.
 */
public interface DeleteMedicalHistoryPort {
    /**
     * Delete a medical history by id.
     *
     * @param id Id of the medical history to delete.
     * @return Result with the success or the failure
     */
    Result<Void, MedicalHistoryFailure> deleteById(Long id);
}
