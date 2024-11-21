package com.vet.hc.api.patient.medicalhistory.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO class for MedicalHistory entity.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalHistoryDto {
    private Long id;

    private String description;
}
