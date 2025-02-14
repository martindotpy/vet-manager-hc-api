package com.vluepixel.vetmanager.api.patient.species.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Species DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpeciesDto {
    private Long id;

    private String name;
}
