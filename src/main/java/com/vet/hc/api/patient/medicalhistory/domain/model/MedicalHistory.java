package com.vet.hc.api.patient.medicalhistory.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a medical history.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalHistory {
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
