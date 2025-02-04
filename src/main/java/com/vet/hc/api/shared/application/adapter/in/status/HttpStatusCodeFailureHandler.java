package com.vet.hc.api.shared.application.adapter.in.status;

import com.vet.hc.api.shared.domain.failure.Failure;

/**
 * Handler for HTTP status codes for failures.
 *
 * @param <T> the type of the failure.
 */
public interface HttpStatusCodeFailureHandler<T extends Failure> {
    /**
     * Get the HTTP status code for a failure.
     *
     * @param failure the failure.
     * @return the HTTP status code.
     */
    int getHttpStatusCode(T failure);
}
