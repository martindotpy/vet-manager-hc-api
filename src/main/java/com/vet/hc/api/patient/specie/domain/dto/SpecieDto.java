package com.vet.hc.api.patient.specie.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for the specie entity.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpecieDto {
    private Long id;

    private String name;
}
