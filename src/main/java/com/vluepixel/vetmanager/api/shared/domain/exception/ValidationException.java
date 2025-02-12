package com.vluepixel.vetmanager.api.shared.domain.exception;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

import lombok.Getter;

@Getter
public final class ValidationException extends RuntimeException {
    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }
}
