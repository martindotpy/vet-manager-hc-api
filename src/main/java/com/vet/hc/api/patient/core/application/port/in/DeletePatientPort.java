package com.vet.hc.api.patient.core.application.port.in;

import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a patient.
 */
public interface DeletePatientPort {
    Result<Void, PatientFailure> deleteById(Long id);
}
