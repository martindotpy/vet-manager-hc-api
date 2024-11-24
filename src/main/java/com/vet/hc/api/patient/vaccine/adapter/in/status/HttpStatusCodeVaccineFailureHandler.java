package com.vet.hc.api.patient.vaccine.adapter.in.status;

import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for vaccine failures.
 */
public final class HttpStatusCodeVaccineFailureHandler implements HttpStatusCodeFailureHandler<VaccineFailure> {
    @Override
    public int getHttpStatusCode(VaccineFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
