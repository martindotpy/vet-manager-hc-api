package com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.CreateMedicalHistoryRequest;

/**
 * Create medical history port.
 */
public interface CreateMedicalHistoryPort {
    /**
     * Create medical history.
     *
     * @param request the create medical history request.
     * @return the created medical history
     */
    MedicalHistoryDto create(CreateMedicalHistoryRequest request);
}
