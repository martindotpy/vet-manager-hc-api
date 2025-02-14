package com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.UpdateMedicalHistoryRequest;

/**
 * Update medical history port.
 */
public interface UpdateMedicalHistoryPort {
    /**
     * Update medical history.
     *
     * @param patientId the patient id.
     * @param request   the update medical history request.
     * @return the updated medical history
     */
    MedicalHistoryDto update(Long patientId, UpdateMedicalHistoryRequest request);
}
