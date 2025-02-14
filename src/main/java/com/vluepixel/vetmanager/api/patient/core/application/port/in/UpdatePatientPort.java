package com.vluepixel.vetmanager.api.patient.core.application.port.in;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.patient.core.domain.request.UpdatePatientRequest;

/**
 * Update patient port.
 */
public interface UpdatePatientPort {
    /**
     * Update patient.
     *
     * @param request the update patient request.
     * @return the updated patient
     */
    PatientDto update(UpdatePatientRequest request);
}
