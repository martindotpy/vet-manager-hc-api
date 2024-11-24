package com.vet.hc.api.patient.species.application.port.in;

import java.util.List;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the species.
 */
public interface FindSpeciesPort {
    /**
     * Find all species.
     *
     * @return the list of species
     */
    List<SpeciesDto> findAll();

    /**
     * Find the species by id.
     *
     * @param id the species id.
     * @return the species found or the failure
     */
    Result<SpeciesDto, SpeciesFailure> findById(Long id);
}
