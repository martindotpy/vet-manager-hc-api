package com.vet.hc.api.patient.core.adapter.in.status;

import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for patient details failures.
 */
public final class HttpStatusCodePatientFailureHandler implements HttpStatusCodeFailureHandler<PatientFailure> {
    @Override
    public int getHttpStatusCode(PatientFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case FIELD_NOT_FOUND -> 404;
            case CLIENT_NOT_FOUND -> 404;
            case RACE_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
