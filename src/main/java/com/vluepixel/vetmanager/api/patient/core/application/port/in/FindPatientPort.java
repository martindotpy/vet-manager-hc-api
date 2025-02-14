package com.vluepixel.vetmanager.api.patient.core.application.port.in;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find patient port.
 */
public interface FindPatientPort {
    /**
     * Find all patient.
     *
     * @param criteria the paginated criteria.
     * @return the paginated patient.
     */
    Paginated<PatientDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find patient by id.
     *
     * @param id the id.
     * @return the patient
     */
    PatientDto findById(Long id);
}
