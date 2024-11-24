package com.vet.hc.api.patient.medicalhistory.application.port.in;

import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.payload.CreateMedicalHistoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a medical history.
 */
public interface CreateMedicalHistoryPort {
    /**
     * Create a medical history.
     *
     * @param payload Payload with the data to create the medical history.
     * @return Result with the medical history created or the failure
     */
    Result<MedicalHistoryDto, MedicalHistoryFailure> create(CreateMedicalHistoryPayload payload);
}
