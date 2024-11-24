package com.vet.hc.api.patient.race.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a race.
 */
public interface UpdateRacePayload extends Payload {
    Long getId();

    String getName();

    Long getSpeciesId();
}
