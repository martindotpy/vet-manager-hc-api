package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.port.in.UpdatePatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.payload.UpdatePatientPayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdatePatientUseCase implements UpdatePatientPort {

    @Override
    public Result<PatientDto, PatientFailure> update(UpdatePatientPayload payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
