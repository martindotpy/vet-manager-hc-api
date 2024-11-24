package com.vet.hc.api.patient.species.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for species.
 *
 * @see com.vet.hc.api.patient.species.domain.model.Species
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpeciesDto {
    private Long id;

    private String name;
}
