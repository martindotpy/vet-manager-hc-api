package com.vet.hc.api.medicalrecord.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add treatment to a medical record.
 */
public interface AddTreatmentToMedicalRecordPort {
    /**
     * Add treatment to an medical record.
     *
     * @param payload The treatment to add to the medical record.
     * @return The result of the operation
     */
    Result<MedicalRecordDto, MedicalRecordFailure> add(CreateTreatmentPayload payload);
}
