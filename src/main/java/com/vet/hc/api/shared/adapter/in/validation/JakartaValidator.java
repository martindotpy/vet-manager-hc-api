package com.vet.hc.api.shared.adapter.in.validation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.shared.domain.validation.ExternalPayloadValidator;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import jakarta.inject.Inject;
import jakarta.validation.Validator;

/**
 * Represents a Jakarta validator.
 */
public final class JakartaValidator implements ExternalPayloadValidator {
    private Validator validator;

    @Inject
    public JakartaValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T extends Payload> List<ValidationError> validate(T payload) {
        var violations = validator.validate(payload);

        if (violations.isEmpty()) {
            return List.of();
        }

        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        violations.forEach(violation -> {
            checkNotNull(violation, "Violation cannot be null");
            checkNotNull(violation.getPropertyPath(), "Property path cannot be null");
            checkNotNull(violation.getMessage(), "Message cannot be null");

            var path = payload.getClass().getSimpleName() + "." + violation.getPropertyPath().toString();
            var message = violation.getMessage();

            validationErrors.add(new ValidationError(path, message));
        });

        return validationErrors;
    }
}
