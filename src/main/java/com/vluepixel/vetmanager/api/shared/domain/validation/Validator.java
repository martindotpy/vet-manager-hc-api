package com.vluepixel.vetmanager.api.shared.domain.validation;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;

import lombok.RequiredArgsConstructor;

/**
 * Validator class.
 *
 * @see Validation
 */
@RequiredArgsConstructor
public final class Validator {
    /**
     * Validate a list of validations.
     *
     * @param validations Validations to validate
     * @throws ValidationException If any validation fails
     */
    public static void validate(Validation... validations) {
        var validationErrors = List.of(validations).stream()
                .map(Validation::validate)
                .flatMap(List::stream)
                .toList();

        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
    }
}
