package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.medicalhistory.domain.payload.CreateMedicalHistoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add medical history to patient port.
 */
public interface AddMedicalHistoryToPatientPort {
    /**
     * Add a medical history to a patient.
     *
     * @param payload The payload with the data to create the medical history.
     * @return The result of the operation
     */
    Result<PatientDto, PatientFailure> add(CreateMedicalHistoryPayload payload);
}
