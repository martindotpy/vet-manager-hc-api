package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.vaccine.domain.payload.CreateVaccinePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add vaccine to patient port.
 */
public interface AddVaccineToPatientPort {
    /**
     * Add a vaccine to a patient.
     *
     * @param payload The payload with the data to create the vaccine.
     * @return The result of the operation
     */
    Result<PatientDto, PatientFailure> add(CreateVaccinePayload payload);
}
