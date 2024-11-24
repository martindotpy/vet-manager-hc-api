package com.vet.hc.api.patient.race.domain.dto;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for race.
 *
 * @see com.vet.hc.api.patient.race.domain.model.Race
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RaceDto {
    private Long id;

    private String name;
    private SpeciesDto species;
}
