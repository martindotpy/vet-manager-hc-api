package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.UpdateTreatmentRequest;

/**
 * Update treatment port.
 */
public interface UpdateTreatmentPort {
    /**
     * Update treatment.
     *
     * @param patientId the patient id.
     * @param medicalRecordId the medical record id.
     * @param request   the update treatment request.
     * @return the updated treatment
     */
    TreatmentDto update(Long patientId, Long medicalRecordId, UpdateTreatmentRequest request);
}
