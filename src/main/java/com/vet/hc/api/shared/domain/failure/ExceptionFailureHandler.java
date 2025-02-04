package com.vet.hc.api.shared.domain.failure;

import com.vet.hc.api.shared.domain.result.Result;

/**
 * Exception failure handler.
 */
public interface ExceptionFailureHandler<F extends Failure> {
    /**
     * Handle the exception.
     *
     * @param e the exception
     * @return the failure
     */
    <T> Result<T, F> handle(Throwable e);
}
