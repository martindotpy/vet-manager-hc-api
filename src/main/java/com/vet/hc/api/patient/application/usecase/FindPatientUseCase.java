package com.vet.hc.api.patient.application.usecase;

import com.vet.hc.api.patient.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.patient.domain.failure.PatientFailure;
import com.vet.hc.api.patient.domain.query.PaginatedPatient;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindPatientUseCase implements FindPatientPort {

    @Override
    public Result<PaginatedPatient, PatientFailure> match(Criteria criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'match'");
    }

    @Override
    public Result<PatientDto, PatientFailure> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
