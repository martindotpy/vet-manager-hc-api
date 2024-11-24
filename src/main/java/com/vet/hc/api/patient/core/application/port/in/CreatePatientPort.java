package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.payload.CreatePatientPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a patient.
 */
public interface CreatePatientPort {
    Result<PatientDto, PatientFailure> create(CreatePatientPayload payload);
}
