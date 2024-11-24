package com.vet.hc.api.patient.medicalhistory.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new medical history.
 */
public interface CreateMedicalHistoryPayload extends Payload {
    String getContent();

    Long getPatientId();
}
