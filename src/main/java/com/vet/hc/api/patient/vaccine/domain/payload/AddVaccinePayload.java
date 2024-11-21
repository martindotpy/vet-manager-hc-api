package com.vet.hc.api.patient.vaccine.domain.payload;

import org.apache.commons.compress.harmony.pack200.NewAttributeBands.Integral;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to add new vacine to a patient.
 */
public interface AddVaccinePayload extends Payload {
    Long getPatientId();

    String getName();

    Integral getDose();

    Long getVaccinatorId();
}
