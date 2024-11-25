package com.vet.hc.api.medicalrecord.treatment.application.port.in;

import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a treatment by id.
 */
public interface DeleteTreatmentPort {
    /**
     * Delete a treatment by id.
     *
     * @param id Id of the treatment to delete.
     * @return Result with the success or the failure
     */
    Result<Void, TreatmentFailure> deleteById(Long id);
}
