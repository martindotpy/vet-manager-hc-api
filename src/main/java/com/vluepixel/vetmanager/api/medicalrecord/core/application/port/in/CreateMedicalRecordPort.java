package com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.CreateMedicalRecordRequest;

/**
 * Create medical record port.
 */
public interface CreateMedicalRecordPort {
    /**
     * Create medical record.
     *
     * @param request   the create medical record request.
     * @return the created medical record
     */
    MedicalRecordDto create(CreateMedicalRecordRequest request);
}
