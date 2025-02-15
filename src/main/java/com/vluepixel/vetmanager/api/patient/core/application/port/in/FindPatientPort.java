package com.vluepixel.vetmanager.api.patient.core.application.port.in;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

/**
 * Find patient port.
 */
public interface FindPatientPort {
    /**
     * Find all patient by paginated criteria.
     *
     * @param criteria the paginated criteria.
     * @return patients found paginated
     */
    Paginated<PatientDto> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Find patient by id.
     *
     * @param id the id.
     * @return patient found
     */
    PatientDto findById(Long id);
}
