package com.vluepixel.vetmanager.api.shared.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a validation error.
 */
@Getter
@ToString
@RequiredArgsConstructor
public final class ValidationError {
    private final String field;
    private final String message;
}
