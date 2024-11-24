package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.query.PaginatedPatient;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding a patient.
 */
public interface FindPatientPort {
    /**
     * Find a patient by its id.
     *
     * @param id The id of the patient.
     * @return The patient if found, otherwise a failure.
     */
    Result<PatientDto, PatientFailure> findById(Long id);

    /**
     * Find all matching patients.
     *
     * @param criteria The criteria to filter the patients.
     * @return The matching patients
     */
    Result<PaginatedPatient, PatientFailure> match(Criteria criteria);
}
