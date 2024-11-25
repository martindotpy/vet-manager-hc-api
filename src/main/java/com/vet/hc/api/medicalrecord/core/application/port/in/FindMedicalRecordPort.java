package com.vet.hc.api.medicalrecord.core.application.port.in;

import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the medical record.
 */
public interface FindMedicalRecordPort {
    /**
     * Find the medical record by id.
     *
     * @param id the medical record id.
     * @return the medical record found or the failure
     */
    Result<MedicalRecordDto, MedicalRecordFailure> findById(Long id);
}
