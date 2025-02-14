package com.vluepixel.vetmanager.api.vaccine.core.application.port.in;

import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.UpdateVaccineRequest;

/**
 * Update vaccine port.
 */
public interface UpdateVaccinePort {
    /**
     * Update vaccine.
     *
     * @param patientId the patient id.
     * @param request   the update vaccine request.
     * @return the updated vaccine
     */
    VaccineDto update(Long patientId, UpdateVaccineRequest request);
}
