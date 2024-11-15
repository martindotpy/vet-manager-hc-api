package com.vet.hc.api.patient.application.port.in;

import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.patient.domain.failure.PatientFailure;
import com.vet.hc.api.patient.domain.query.PaginatedPatient;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

public interface FindPatientPort {
    Result<PaginatedPatient, PatientFailure> match(Criteria criteria);

    Result<PatientDto, PatientFailure> findById(Long id);
}
