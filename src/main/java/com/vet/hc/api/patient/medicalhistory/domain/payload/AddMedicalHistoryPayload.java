package com.vet.hc.api.patient.medicalhistory.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to add new medical history to a patient.
 */
public interface AddMedicalHistoryPayload extends Payload {
    Long getPatientId();

    String getContent();
}
