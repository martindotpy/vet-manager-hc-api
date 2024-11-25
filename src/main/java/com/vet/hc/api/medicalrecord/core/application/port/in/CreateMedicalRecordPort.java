package com.vet.hc.api.medicalrecord.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.payload.CreateMedicalRecordPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a medical record.
 */
public interface CreateMedicalRecordPort {
    /**
     * Create a medical record.
     *
     * @param payload Payload with the data to create the medical record.
     * @return Result with the medical record created or the failure
     */
    Result<MedicalRecordDto, MedicalRecordFailure> create(CreateMedicalRecordPayload payload);
}
