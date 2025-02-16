package com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in;

import java.util.List;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;

/**
 * Find medical history port.
 */
public interface FindMedicalHistoryPort {
    /**
     * Find all medical history by patient id.
     *
     * @param patientId the patient id.
     * @return the medical history.
     */
    List<MedicalHistoryDto> findAllByPatientId(Long patientId);
}
