package com.vet.hc.api.medicalrecord.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.payload.UpdateMedicalRecordPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a medical record.
 */
public interface UpdateMedicalRecordPort {
    /**
     * Update a medical record.
     *
     * @param payload Payload with the data to update the medical record.
     * @return Result with the medical record updated or the failure
     */
    Result<MedicalRecordDto, MedicalRecordFailure> update(UpdateMedicalRecordPayload payload);
}
