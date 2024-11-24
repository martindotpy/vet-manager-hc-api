package com.vet.hc.api.patient.race.application.port.in;

import java.util.List;

import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the race.
 */
public interface FindRacePort {
    /**
     * Find all races.
     *
     * @return the list of races
     */
    List<RaceDto> findAll();

    /**
     * Find the race by id.
     *
     * @param id the race id.
     * @return the race found or the failure
     */
    Result<RaceDto, RaceFailure> findById(Long id);
}
