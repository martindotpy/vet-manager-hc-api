package com.vet.hc.api.patient.species.application.port.in;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.payload.UpdateSpeciesPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a species.
 */
public interface UpdateSpeciesPort {
    /**
     * Update a species.
     *
     * @param payload Payload with the data to update the species.
     * @return Result with the species updated or the failure
     */
    Result<SpeciesDto, SpeciesFailure> update(UpdateSpeciesPayload payload);
}
