package com.vet.hc.api.patient.species.domain.dto;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for species.
 *
 * @see com.vet.hc.api.patient.species.domain.model.Species
 */
@ColumnClassName("Especie")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpeciesDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
}
