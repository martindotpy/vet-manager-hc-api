package com.vet.hc.api.shared.domain.repository;

/**
 * Repository failure handler.
 */
public interface RepositoryErrorHandler {
    /**
     * Handle the exception.
     *
     * @param e the exception
     * @return the failure
     */
    RepositoryError handle(Throwable e);
}
