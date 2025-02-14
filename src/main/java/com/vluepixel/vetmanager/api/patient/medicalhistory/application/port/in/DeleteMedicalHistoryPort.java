package com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in;

/**
 * Delete medical history port.
 */
public interface DeleteMedicalHistoryPort {
    /**
     * Delete medical history by id.
     *
     * @param patientId the patient id.
     * @param id        the medical history id.
     */
    void deleteByPatientIdAndId(Long patientId, Long id);
}
