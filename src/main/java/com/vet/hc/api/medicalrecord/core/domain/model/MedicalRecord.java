package com.vet.hc.api.medicalrecord.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.annotation.Nullable;
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

    private LocalDateTime entryTime;
    private String reason;
    private String physicalExamination;
    private Float celsiusTemperature;
    private Float respiratoryRate;
    private Float heartRate;
    private Float weight;
    private boolean sterilized;
    private String supplementaryExamination;
    private String recipe;
    private String diagnosis;

    @Nullable
    private Patient patient;
    private User vet;
    private List<Treatment> treatments;
}
