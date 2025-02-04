package com.vet.hc.api.user.core.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;
import com.vet.hc.api.user.core.domain.failure.UserFailure;

/**
 * Handler for HTTP status codes for user failures.
 */
@Component
public final class HttpStatusCodeUserFailureProvider implements HttpStatusCodeFailureHandler<UserFailure> {
    @Override
    public int getHttpStatusCode(UserFailure failure) {
        return switch (failure) {
            case USER_CANNOT_DELETE_THEMSELF -> 403;
            case NOT_FOUND -> 404;
            case EMAIL_ALREADY_IN_USE -> 409;
            case FIRST_NAME_REQUIRED -> 422;
            case LAST_NAME_REQUIRED -> 422;
            case UNEXPECTED -> 500;
        };
    }
}
