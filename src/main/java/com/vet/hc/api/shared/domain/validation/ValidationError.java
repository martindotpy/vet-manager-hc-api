package com.vet.hc.api.shared.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a validation error.
 */
@Getter
@RequiredArgsConstructor
public final class ValidationError {
    private final String field;
    private final String message;
}
