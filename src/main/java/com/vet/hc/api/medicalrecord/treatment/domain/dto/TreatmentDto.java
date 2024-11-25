package com.vet.hc.api.medicalrecord.treatment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for treatment.
 *
 * @see com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TreatmentDto {
    private Long id;

    private Integer order;
    private String description;
}
