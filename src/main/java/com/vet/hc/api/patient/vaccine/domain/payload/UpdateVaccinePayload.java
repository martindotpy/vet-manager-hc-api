package com.vet.hc.api.patient.vaccine.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating a vaccine.
 */
public interface UpdateVaccinePayload extends Payload {
    Long getId();

    String getName();

    Integer getDose();

    LocalDateTime getVaccinatedAt();

    Long getPatientId();

    Long getVaccinatorId();

    Long getProductSaleId();
}
