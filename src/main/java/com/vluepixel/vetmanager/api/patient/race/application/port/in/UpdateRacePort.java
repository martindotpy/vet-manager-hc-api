package com.vluepixel.vetmanager.api.patient.race.application.port.in;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.patient.race.domain.request.UpdateRaceRequest;

/**
 * Update race port.
 */
public interface UpdateRacePort {
    /**
     * Update race.
     *
     * @param request the update race request.
     * @return the updated race
     */
    RaceDto update(UpdateRaceRequest request);
}
