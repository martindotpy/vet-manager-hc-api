package com.vluepixel.vetmanager.api.patient.race.application.port.in;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.patient.race.domain.request.CreateRaceRequest;

/**
 * Create race port.
 */
public interface CreateRacePort {
    /**
     * Create race.
     *
     * @param request the create race request.
     * @return the created race
     */
    RaceDto create(CreateRaceRequest request);
}
