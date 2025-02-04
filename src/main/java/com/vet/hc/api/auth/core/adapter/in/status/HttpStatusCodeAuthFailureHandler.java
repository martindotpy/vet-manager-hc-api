package com.vet.hc.api.auth.core.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for authentication failures.
 */
@Component
public final class HttpStatusCodeAuthFailureHandler implements HttpStatusCodeFailureHandler<AuthFailure> {
    @Override
    public int getHttpStatusCode(AuthFailure failure) {
        return switch (failure) {
            case INVALID_CREDENTIALS -> 401;
            case EMAIL_ALREADY_IN_USE -> 409;
            case ALREADY_AUTHENTICATED -> 409;
            case UNEXPECTED -> 500;
        };
    }
}
