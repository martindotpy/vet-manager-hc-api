package com.vet.hc.api.shared.adapter.in.validation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.shared.domain.validation.ExternalPayloadValidator;
import com.vet.hc.api.shared.domain.validation.ValidationError;

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
        log.info("Validating payload `{}`", payload.getClass().getSimpleName());

        var violations = validator.validate(payload);

        if (violations.isEmpty()) {
            return new CopyOnWriteArrayList<>();
        }

        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        violations.forEach(violation -> {
            checkNotNull(violation, "Violation cannot be null");
            checkNotNull(violation.getPropertyPath(), "Property path cannot be null");
            checkNotNull(violation.getMessage(), "Message cannot be null");

            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            String requestClassName = payload.getClass().getSimpleName();

            log.debug("Violation on path `{}` of type `{}`",
                    path,
                    requestClassName);

            validationErrors.add(new ValidationError(requestClassName + "." + path, message));
        });

        return validationErrors;
    }
}
