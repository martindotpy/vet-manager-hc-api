package com.vet.hc.api.patient.specie.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a specie.
 */
public interface CreateSpeciePayload extends Payload {
    String getName();
}
