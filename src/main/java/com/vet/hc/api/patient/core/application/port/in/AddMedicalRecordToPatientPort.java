package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.payload.CreateMedicalRecordPayload;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add medical record to patient port.
 */
public interface AddMedicalRecordToPatientPort {
    /**
     * Add a medical record to a patient.
     *
     * @param payload The payload with the data to create the medical record.
     * @return The result of the operation
     */
    Result<PatientDto, PatientFailure> add(CreateMedicalRecordPayload payload);
}
