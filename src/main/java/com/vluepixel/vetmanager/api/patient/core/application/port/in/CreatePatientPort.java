package com.vluepixel.vetmanager.api.patient.core.application.port.in;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.patient.core.domain.request.CreatePatientRequest;

/**
 * Create patient port.
 */
public interface CreatePatientPort {
    /**
     * Create patient.
     *
     * @param request the create patient request.
     * @return the created patient
     */
    PatientDto create(CreatePatientRequest request);
}
