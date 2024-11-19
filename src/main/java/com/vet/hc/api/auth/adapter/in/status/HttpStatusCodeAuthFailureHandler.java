package com.vet.hc.api.auth.adapter.in.status;

import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for authentication failures.
 */
public final class HttpStatusCodeAuthFailureHandler implements HttpStatusCodeFailureHandler<AuthFailure> {
    @Override
    public int getHttpStatusCode(AuthFailure failure) {
        return switch (failure) {
            case EMAIL_ALREADY_IN_USE -> 409;
            case INVALID_CREDENTIALS -> 401;
        };
    }
}
