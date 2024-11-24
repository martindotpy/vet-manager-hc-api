package com.vet.hc.api.patient.species.application.port.in;

import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a species by id.
 */
public interface DeleteSpeciesPort {
    /**
     * Delete a species by id.
     *
     * @param id Id of the species to delete.
     * @return Result with the success or the failure
     */
    Result<Void, SpeciesFailure> deleteById(Long id);
}
