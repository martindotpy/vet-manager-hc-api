package com.vet.hc.api.patient.medicalhistory.application.port.in;

import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a medical history.
 */
public interface UpdateMedicalHistoryPort {
    /**
     * Update a medical history.
     *
     * @param payload Payload with the data to update the medical history.
     * @return Result with the medical history updated or the failure
     */
    Result<MedicalHistoryDto, MedicalHistoryFailure> update(UpdateMedicalHistoryPayload payload);
}
