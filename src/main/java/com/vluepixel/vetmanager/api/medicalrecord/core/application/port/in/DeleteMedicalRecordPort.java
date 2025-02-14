package com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in;

/**
 * Delete medical record port.
 */
public interface DeleteMedicalRecordPort {
    /**
     * Delete medical record by id.
     *
     * @param patientId the patient id.
     * @param id        the medical record id.
     */
    void deleteByPatientIdAndId(Long patientId, Long id);
}
