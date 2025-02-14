package com.vluepixel.vetmanager.api.patient.species.application.port.in;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.domain.request.CreateSpeciesRequest;

/**
 * Create species port.
 */
public interface CreateSpeciesPort {
    /**
     * Create species.
     *
     * @param request the create species request.
     * @return the created species
     */
    SpeciesDto create(CreateSpeciesRequest request);
}
