package com.vluepixel.vetmanager.api.vaccine.core.application.port.in;

import java.util.List;

import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;

/**
 * Find vaccine port.
 */
public interface FindVaccinePort {
    /**
     * Find all vaccine by patient id.
     *
     * @param patientId the patient id.
     * @return vaccines found
     */
    List<VaccineDto> findAllByPatientId(Long patientId);

    /**
     * Find vaccine by id.
     *
     * @param id the id.
     * @return vaccine found
     */
    VaccineDto findById(Long id);
}
