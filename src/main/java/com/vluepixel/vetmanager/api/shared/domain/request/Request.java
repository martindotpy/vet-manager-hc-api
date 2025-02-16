package com.vluepixel.vetmanager.api.shared.domain.request;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.validation.ExternalRequestValidatorProvider;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

/**
 * Marker interface for requests.
 */
public interface Request {
    /**
     * Validate the request.
     *
     * @return the list of validation errors
     */
    default List<ValidationError> validate() {
        return ExternalRequestValidatorProvider.get().validate(this);
    }
}
