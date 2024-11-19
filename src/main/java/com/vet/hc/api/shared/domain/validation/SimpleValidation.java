package com.vet.hc.api.shared.domain.validation;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * SimpleValidation class.
 *
 * @see Validation
 */
@Getter
@RequiredArgsConstructor
public final class SimpleValidation implements Validation {
    private final boolean evaluatedExpression;
    private final String field;
    private final String message;

    @Override
    public List<ValidationError> validate() {
        return evaluatedExpression ? List.of() : List.of(new ValidationError(field, message));
    }
}
