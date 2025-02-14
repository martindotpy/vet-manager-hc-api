package com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in;

import java.util.List;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;

/**
 * Find medical record port.
 */
public interface FindMedicalRecordPort {
    /**
     * Find all medical record by patient id.
     *
     * @param patientId the patient id.
     * @return the medical record.
     */
    List<MedicalRecordDto> findAllByPatientId(Long patientId);

    /**
     * Find medical record by id.
     *
     * @param id the id.
     * @return the medical record
     */
    MedicalRecordDto findById(Long id);
}
