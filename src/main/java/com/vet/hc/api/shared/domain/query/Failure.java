package com.vet.hc.api.shared.domain.query;

/**
 * Response for failed operations.
 *
 * <p>
 * This interface is used to indicate that the operation failed.
 * </p>
 */
public interface Failure {
    String getMessage();
}
