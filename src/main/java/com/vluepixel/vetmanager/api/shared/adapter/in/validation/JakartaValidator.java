package com.vluepixel.vetmanager.api.shared.adapter.in.validation;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightYellow;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;
import com.vluepixel.vetmanager.api.shared.domain.validation.ExternalPayloadValidator;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a Jakarta validator.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class JakartaValidator implements ExternalPayloadValidator {
    private final Validator validator;

    @Override
    public <T extends Payload> List<ValidationError> validate(T payload) {
        log.info("Validating payload {}",
                fgBrightYellow(payload.getClass().getSimpleName()));

        var violations = validator.validate(payload);

        if (violations.isEmpty()) {
            return new CopyOnWriteArrayList<>();
        }

        List<ValidationError> validationErrors = fromViolations(violations);

        return validationErrors;
    }

    /**
     * Converts a collection of constraint violations to a list of validation
     * errors.
     *
     * @param violations the collection of constraint violations.
     * @return the list of validation errors
     */
    public static List<ValidationError> fromViolations(Collection<? extends ConstraintViolation<?>> violations) {
        return violations.stream()
                .peek(v -> {
                    Objects.requireNonNull(v, "Violation cannot be null");
                    Objects.requireNonNull(v.getPropertyPath(), "Property path cannot be null");
                    Objects.requireNonNull(v.getMessage(), "Message cannot be null");
                })
                .map(v -> {
                    String path = v.getPropertyPath().toString();
                    log.debug("Violation on path {} of type {}",
                            fgBrightRed(path),
                            fgBrightRed(v.getRootBeanClass().getSimpleName()));
                    return new ValidationError(path, v.getMessage());
                })
                .collect(Collectors.toList());
    }
}
