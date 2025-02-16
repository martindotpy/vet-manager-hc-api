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
     * @return medical records found
     */
    List<MedicalRecordDto> findAllByPatientId(Long patientId);
}
