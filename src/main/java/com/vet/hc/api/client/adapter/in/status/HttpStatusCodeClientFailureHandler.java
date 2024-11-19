package com.vet.hc.api.client.adapter.in.status;

import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for client failures.
 */
public final class HttpStatusCodeClientFailureHandler implements HttpStatusCodeFailureHandler<ClientFailure> {
    @Override
    public int getHttpStatusCode(ClientFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case EMAIL_ALREADY_IN_USE -> 409;
            case PHONE_ALREADY_IN_USE -> 409;
            case EMAIL_SAVE_ERROR -> 500;
            case PHONE_SAVE_ERROR -> 500;
            case FIELD_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
