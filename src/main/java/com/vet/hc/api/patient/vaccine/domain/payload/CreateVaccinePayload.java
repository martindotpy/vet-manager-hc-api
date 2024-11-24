package com.vet.hc.api.patient.vaccine.domain.payload;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new vaccine.
 */
public interface CreateVaccinePayload extends Payload {
    String getName();

    Integer getDose();

    LocalDateTime getVaccinatedAt();

    Long getPatientId();

    Long getVaccinatorId();

    Long getProductSaleId();
}
