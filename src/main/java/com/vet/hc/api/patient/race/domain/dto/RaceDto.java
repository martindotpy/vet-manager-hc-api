package com.vet.hc.api.patient.race.domain.dto;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for race.
 *
 * @see com.vet.hc.api.patient.race.domain.model.Race
 */
@ColumnClassName("Raza")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RaceDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
    @ColumnPropertyName("Especie")
    private SpeciesDto species;
}
