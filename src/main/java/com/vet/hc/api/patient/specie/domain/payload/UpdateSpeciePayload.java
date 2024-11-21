package com.vet.hc.api.patient.specie.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update a specie.
 */
public interface UpdateSpeciePayload extends Payload {
    Long getId();

    String getName();
}
