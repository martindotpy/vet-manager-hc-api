package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.payload.UpdatePatientPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a patient.
 */
public interface UpdatePatientPort {
    /**
     * Update a patient.
     *
     * @param payload the payload to update a patient.
     * @return the updated patient
     */
    Result<PatientDto, PatientFailure> update(UpdatePatientPayload payload);
}
