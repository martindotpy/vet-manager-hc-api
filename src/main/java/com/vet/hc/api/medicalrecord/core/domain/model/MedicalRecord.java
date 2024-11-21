package com.vet.hc.api.medicalrecord.core.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a medical record.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    private Long id;

    private LocalDateTime entryDatetime;
    private String reason;
    private String physicalExamination;
    private Double celsiusTemperature;
    private Double respiratoryRate;
    private Double heartRate;
    private Double weight;
    private boolean isSterilized;
    private String supplementaryExamination;
    private String recipe;
    private String diagnosis;

    private Patient patient;
    private User attendedBy;
}
