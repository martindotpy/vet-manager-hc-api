package com.vet.hc.api.shared.domain.failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Failure for global exceptions thrown by the application.
 */
@Getter
@RequiredArgsConstructor
public enum GlobalFailure implements Failure {
    NOT_FOUND("Resource not found"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    METHOD_NOT_ALLOWED("Method not allowed"),
    UNSUPPORTED_OPERATION("Unsupported operation");

    private final String message;
}
