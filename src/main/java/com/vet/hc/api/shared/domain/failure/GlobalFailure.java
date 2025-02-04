package com.vet.hc.api.shared.domain.failure;

import lombok.RequiredArgsConstructor;

/**
 * Failure for global exceptions thrown by the application.
 */
@RequiredArgsConstructor
public enum GlobalFailure implements Failure {
    NOT_FOUND("Resource not found"),
    FORBIDDEN("Forbidden"),
    INVALID_ENUM_VALUE("Only the following values are allowed: %s"),
    ILLEGAL_ARGUMENT("%s"),
    JSON_PARSE_ERROR("JSON parse error at row %s, column %s: %s"),
    CANNOT_PARSE_DATETIME("Cannot parse datetime: %s"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    METHOD_NOT_ALLOWED("Method not allowed"),
    UNSUPPORTED_OPERATION("Unsupported operation");

    private Object[] args;
    private final String message;

    @Override
    public String getMessage() {
        return String.format(message, args);
    }

    public void setArgs(Object... args) {
        this.args = args;
    }
}
