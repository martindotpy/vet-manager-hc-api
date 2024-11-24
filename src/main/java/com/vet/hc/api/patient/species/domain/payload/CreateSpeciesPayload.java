package com.vet.hc.api.patient.species.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new species.
 */
public interface CreateSpeciesPayload extends Payload {
    String getName();
}
