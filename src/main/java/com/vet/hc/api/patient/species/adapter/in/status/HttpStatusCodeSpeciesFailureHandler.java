package com.vet.hc.api.patient.species.adapter.in.status;

import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for species failures.
 */
public final class HttpStatusCodeSpeciesFailureHandler implements HttpStatusCodeFailureHandler<SpeciesFailure> {
    @Override
    public int getHttpStatusCode(SpeciesFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATED_NAME -> 409;
            case UNEXPECTED -> 500;
        };
    }
}
