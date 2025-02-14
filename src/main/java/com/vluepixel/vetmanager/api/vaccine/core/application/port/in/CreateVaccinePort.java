package com.vluepixel.vetmanager.api.vaccine.core.application.port.in;

import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.CreateVaccineRequest;

/**
 * Create vaccine port.
 */
public interface CreateVaccinePort {
    /**
     * Create vaccine.
     *
     * @param request the create vaccine request.
     * @return the created vaccine
     */
    VaccineDto create(CreateVaccineRequest request);
}
