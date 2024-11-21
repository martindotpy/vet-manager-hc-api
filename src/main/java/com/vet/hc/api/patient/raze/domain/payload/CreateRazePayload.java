package com.vet.hc.api.patient.raze.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a raze.
 */
public interface CreateRazePayload extends Payload {
    String getName();

    Long getSpecieId();
}
