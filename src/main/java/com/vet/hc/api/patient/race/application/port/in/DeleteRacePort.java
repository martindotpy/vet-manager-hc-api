package com.vet.hc.api.patient.race.application.port.in;

import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a race by id.
 */
public interface DeleteRacePort {
    /**
     * Delete a race by id.
     *
     * @param id Id of the race to delete.
     * @return Result with the success or the failure
     */
    Result<Void, RaceFailure> deleteById(Long id);
}
