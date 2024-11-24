package com.vet.hc.api.patient.race.application.port.in;

import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.payload.UpdateRacePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a race.
 */
public interface UpdateRacePort {
    /**
     * Update a race.
     *
     * @param payload Payload with the data to update the race.
     * @return Result with the race updated or the failure
     */
    Result<RaceDto, RaceFailure> update(UpdateRacePayload payload);
}
