package com.vet.hc.api.patient.vaccine.application.port.in;

import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the vaccine.
 */
public interface FindVaccinePort {
    /**
     * Find the vaccine by id.
     *
     * @param id the vaccine id.
     * @return the vaccine found or the failure
     */
    Result<VaccineDto, VaccineFailure> findById(Long id);
}
