package com.vluepixel.vetmanager.api.patient.species.application.port.in;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.domain.request.UpdateSpeciesRequest;

/**
 * Update species port.
 */
public interface UpdateSpeciesPort {
    /**
     * Update species.
     *
     * @param request the update species request.
     * @return the updated species
     */
    SpeciesDto update(UpdateSpeciesRequest request);
}
