package com.vet.hc.api.patient.species.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a species.
 */
public interface UpdateSpeciesPayload extends Payload {
    Long getId();

    String getName();
}
