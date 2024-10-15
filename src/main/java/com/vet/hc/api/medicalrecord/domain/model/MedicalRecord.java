package com.vet.hc.api.medicalrecord.domain.model;

import java.time.Instant;

import com.vet.hc.api.patient.domain.model.Patient;
import com.vet.hc.api.vet.domain.model.Vet;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a medical record.
 */
@Getter
@Builder
public class MedicalRecord {
    private Long id;

    private Instant entryDatetime;
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
    private Vet attendedBy;
}
