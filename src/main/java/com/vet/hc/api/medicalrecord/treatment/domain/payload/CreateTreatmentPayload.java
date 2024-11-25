package com.vet.hc.api.medicalrecord.treatment.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new treatment.
 */
public interface CreateTreatmentPayload extends Payload {
    Integer getOrder();

    String getDescription();

    Long getMedicalRecordId();
}
