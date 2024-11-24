package com.vet.hc.api.patient.race.application.port.in;

import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.payload.CreateRacePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a race.
 */
public interface CreateRacePort {
    /**
     * Create a race.
     *
     * @param payload Payload with the data to create the race.
     * @return Result with the race created or the failure
     */
    Result<RaceDto, RaceFailure> create(CreateRacePayload payload);
}
