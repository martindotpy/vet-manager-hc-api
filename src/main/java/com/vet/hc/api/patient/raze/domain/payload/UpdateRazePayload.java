package com.vet.hc.api.patient.raze.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update a raze.
 */
public interface UpdateRazePayload extends Payload {
    Long getId();

    String getName();

    Long getSpecieId();
}
