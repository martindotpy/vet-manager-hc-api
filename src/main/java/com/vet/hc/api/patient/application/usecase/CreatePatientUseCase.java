package com.vet.hc.api.patient.application.usecase;

import com.vet.hc.api.patient.application.port.in.CreatePatientPort;
import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.patient.domain.failure.PatientFailure;
import com.vet.hc.api.patient.domain.payload.CreatePatientPayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class CreatePatientUseCase implements CreatePatientPort {@Override
    public Result<PatientDto, PatientFailure> create(CreatePatientPayload payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
}
