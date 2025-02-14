package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Treatment DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TreatmentDto {
    private Long id;

    private String description;
    private Integer order;
}
