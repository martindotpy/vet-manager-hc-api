package com.vet.hc.api.shared.domain.validation.impl;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.util.List;

import com.vet.hc.api.shared.domain.validation.Validation;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple validation class.
 *
 * <div>
 * The evaluated expression represents the valid states.
 * <p>
 * Example:
 * <code>new ValidStateValidation(1 == 1, "field", "message").validate()</code>
 * </p>
 *
 * This validation will pass because the expression is true.
 * </div>
 *
 * @see Validation
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class ValidStateValidation implements Validation {
    private final boolean evaluatedExpression;
    private final String field;
    private final String message;

    public static ValidStateValidation of(boolean evaluatedExpression, String field, String message) {
        return new ValidStateValidation(evaluatedExpression, field, message);
    }

    @Override
    public List<ValidationError> validate() {
        if (evaluatedExpression) {
            log.debug("Validation passed for field {}",
                    fgBrightGreen(field));

            return List.of();
        }

        log.warn("Validation failed for field {} with message {}",
                fgBrightRed(field),
                fgBrightRed(message));

        return List.of(new ValidationError(field, message));
    }
}
