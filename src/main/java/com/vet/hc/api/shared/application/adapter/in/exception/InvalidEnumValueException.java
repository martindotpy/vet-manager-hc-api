package com.vet.hc.api.shared.application.adapter.in.exception;

import lombok.Getter;

/**
 * Exception thrown when an invalid enum value is provided.
 */
@Getter
public final class InvalidEnumValueException extends RuntimeException {
    private final Class<? extends Enum<?>> enumType;

    public InvalidEnumValueException(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }
}
