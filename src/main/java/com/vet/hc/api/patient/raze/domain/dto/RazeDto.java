package com.vet.hc.api.patient.raze.domain.dto;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO class for Raze entity.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RazeDto {
    private Long id;

    private String name;

    private SpecieDto specieDto;
}
