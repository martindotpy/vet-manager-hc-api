package com.vet.hc.api.patient.race.adapter.in.status;

import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for race failures.
 */
public final class HttpStatusCodeRaceFailureHandler implements HttpStatusCodeFailureHandler<RaceFailure> {
    @Override
    public int getHttpStatusCode(RaceFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATED_NAME -> 409;
            case UNEXPECTED -> 500;
        };
    }
}
