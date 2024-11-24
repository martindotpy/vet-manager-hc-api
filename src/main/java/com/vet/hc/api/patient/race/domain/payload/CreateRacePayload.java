package com.vet.hc.api.patient.race.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new race.
 */
public interface CreateRacePayload extends Payload {
    String getName();

    Long getSpeciesId();
}
