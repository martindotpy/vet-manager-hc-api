package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in;

/**
 * Delete treatment port.
 */
public interface DeleteTreatmentPort {
    /**
     * Delete treatment by patient id, medical record id and id.
     *
     * @param patientId       the patient id.
     * @param medicalRecordId the medical record id.
     * @param id              the treatment id.
     */
    void deleteByPatientIdAndMedicalRecordIdAndId(Long patientId, Long medicalRecordId, Long id);
}
