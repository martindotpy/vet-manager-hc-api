package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.domain.query.Result;

public interface DeletePatientPort {
    Result<Void, PatientFailure> deleteById(Long id);
}
