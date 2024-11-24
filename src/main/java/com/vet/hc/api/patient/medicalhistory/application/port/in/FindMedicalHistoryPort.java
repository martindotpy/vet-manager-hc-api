package com.vet.hc.api.patient.medicalhistory.application.port.in;

import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the medical history.
 */
public interface FindMedicalHistoryPort {
    /**
     * Find the medical history by id.
     *
     * @param id the medical history id.
     * @return the medical history found or the failure
     */
    Result<MedicalHistoryDto, MedicalHistoryFailure> findById(Long id);
}
