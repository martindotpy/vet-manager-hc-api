package com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Medical history DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalHistoryDto {
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
