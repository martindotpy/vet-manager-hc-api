package com.vet.hc.api.patient.application.port.in;

import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.patient.domain.failure.PatientFailure;
import com.vet.hc.api.patient.domain.payload.CreatePatientPayload;
import com.vet.hc.api.shared.domain.query.Result;

public interface CreatePatientPort {
    Result<PatientDto, PatientFailure> create(CreatePatientPayload payload);
}
