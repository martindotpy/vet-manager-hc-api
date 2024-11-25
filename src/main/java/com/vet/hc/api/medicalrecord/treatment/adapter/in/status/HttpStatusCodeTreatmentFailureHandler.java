package com.vet.hc.api.medicalrecord.treatment.adapter.in.status;

import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for treatment failures.
 */
public final class HttpStatusCodeTreatmentFailureHandler
        implements HttpStatusCodeFailureHandler<TreatmentFailure> {
    @Override
    public int getHttpStatusCode(TreatmentFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
