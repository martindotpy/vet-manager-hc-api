package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in;

import java.util.List;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;

/**
 * Find treatment port.
 */
public interface FindTreatmentPort {
    /**
     * Find all treatment by patient id.
     *
     * @param patientId       the patient id.
     * @param medicalRecordId the medical record id.
     * @return the treatment.
     */
    List<TreatmentDto> findAllByPatientIdAndMedicalRecordId(Long patientId, Long medicalRecordId);

    /**
     * Find treatment by id.
     *
     * @param id the id.
     * @return the treatment
     */
    TreatmentDto findById(Long id);
}
