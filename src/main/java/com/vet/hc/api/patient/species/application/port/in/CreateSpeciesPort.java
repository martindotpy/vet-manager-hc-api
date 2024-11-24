package com.vet.hc.api.patient.species.application.port.in;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.payload.CreateSpeciesPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a species.
 */
public interface CreateSpeciesPort {
    /**
     * Create a species.
     *
     * @param payload Payload with the data to create the species.
     * @return Result with the species created or the failure
     */
    Result<SpeciesDto, SpeciesFailure> create(CreateSpeciesPayload payload);
}
