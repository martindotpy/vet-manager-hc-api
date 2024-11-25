package com.vet.hc.api.medicalrecord.treatment.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a treatment.
 */
public interface UpdateTreatmentPayload extends Payload {
    Long getId();

    Integer getOrder();

    String getDescription();

    Long getMedicalRecordId();
}
