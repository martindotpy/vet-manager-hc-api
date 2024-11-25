package com.vet.hc.api.patient.medicalhistory.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.core.domain.model.Patient;

import jakarta.annotation.Nullable;
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
public class MedicalHistory {
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    @Nullable
    private Patient patient;
}
