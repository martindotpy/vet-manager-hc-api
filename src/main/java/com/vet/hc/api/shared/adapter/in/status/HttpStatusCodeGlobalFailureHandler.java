package com.vet.hc.api.shared.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.domain.failure.GlobalFailure;

/**
 * Handler for HTTP status codes for global failures.
 */
@Component
public final class HttpStatusCodeGlobalFailureHandler implements HttpStatusCodeFailureHandler<GlobalFailure> {
    @Override
    public int getHttpStatusCode(GlobalFailure failure) {
        return switch (failure) {
            case INVALID_ENUM_VALUE -> 400;
            case ILLEGAL_ARGUMENT -> 400;
            case CANNOT_PARSE_DATETIME -> 400;
            case JSON_PARSE_ERROR -> 400;
            case INTERNAL_SERVER_ERROR -> 500;
            case NOT_FOUND -> 404;
            case METHOD_NOT_ALLOWED -> 405;
            case UNSUPPORTED_OPERATION -> 501;
            case FORBIDDEN -> 403;
        };
    }
}
