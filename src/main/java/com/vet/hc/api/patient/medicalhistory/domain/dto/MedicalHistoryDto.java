package com.vet.hc.api.patient.medicalhistory.domain.dto;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for medical history.
 *
 * @see com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalHistoryDto {
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    private PatientDto patient;
}
