package com.vet.hc.api.patient.medicalhistory.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a medical history.
 */
public interface UpdateMedicalHistoryPayload extends Payload {
    Long getId();

    String getContent();

    Long getPatientId();
}
