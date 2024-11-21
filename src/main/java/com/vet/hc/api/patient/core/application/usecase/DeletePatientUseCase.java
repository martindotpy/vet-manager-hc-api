package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.port.in.DeletePatientPort;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeletePatientUseCase implements DeletePatientPort {
    @Override
    public Result<Void, PatientFailure> deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
