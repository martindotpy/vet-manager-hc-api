package com.vet.hc.api.medicalrecord.core.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a medical record.
 */
public interface UpdateMedicalRecordPayload extends Payload {
    Long getId();

    LocalDateTime getEntryTime();

    String getReason();

    String getPhysicalExamination();

    Float getCelsiusTemperature();

    Float getRespiratoryRate();

    Float getHeartRate();

    Float getWeight();

    boolean isSterilized();

    String getSupplementaryExamination();

    String getRecipe();

    String getDiagnosis();

    Long getPatientId();

    Long getVetId();
}
