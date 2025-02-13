package com.vluepixel.vetmanager.api.shared.domain.validation;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

/**
 * Represents a external request validator.
 */
public interface ExternalRequestValidator {
    /**
     * Validates a request.
     *
     * @param request The request to be validated.
     * @return A list of validation errors
     */
    <T extends Request> List<ValidationError> validate(T request);
}
