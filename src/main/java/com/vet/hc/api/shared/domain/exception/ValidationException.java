package com.vet.hc.api.shared.domain.exception;

import java.util.List;

import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.Getter;

@Getter
public final class ValidationException extends RuntimeException {
    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }
}
