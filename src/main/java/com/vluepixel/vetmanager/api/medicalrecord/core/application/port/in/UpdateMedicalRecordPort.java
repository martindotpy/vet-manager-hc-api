package com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.UpdateMedicalRecordRequest;

/**
 * Update medical record port.
 */
public interface UpdateMedicalRecordPort {
    /**
     * Update medical record.
     *
     * @param patientId the patient id.
     * @param request   the update medical record request.
     * @return the updated medical record
     */
    MedicalRecordDto update(Long patientId, UpdateMedicalRecordRequest request);
}
