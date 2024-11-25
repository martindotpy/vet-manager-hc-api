package com.vet.hc.api.medicalrecord.core.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new medical record.
 */
public interface CreateMedicalRecordPayload extends Payload {
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
