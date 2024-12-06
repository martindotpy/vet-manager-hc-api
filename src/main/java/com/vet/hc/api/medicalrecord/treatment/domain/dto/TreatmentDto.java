package com.vet.hc.api.medicalrecord.treatment.domain.dto;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for treatment.
 *
 * @see com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment
 */
@ColumnClassName("Tratamiento")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TreatmentDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Orden")
    private Integer order;
    @ColumnPropertyName("Descripción")
    private String description;
}
