package com.vet.hc.api.patient.vaccine.application.port.in;

import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a vaccine by id.
 */
public interface DeleteVaccinePort {
    /**
     * Delete a vaccine by id.
     *
     * @param id Id of the vaccine to delete.
     * @return Result with the success or the failure
     */
    Result<Void, VaccineFailure> deleteById(Long id);
}
