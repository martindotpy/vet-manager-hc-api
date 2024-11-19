package com.vet.hc.api.shared.domain.payload;

import java.util.List;

import com.vet.hc.api.shared.domain.validation.ExternalPayloadValidatorProvider;
import com.vet.hc.api.shared.domain.validation.ValidationError;

/**
 * Marker interface for payloads.
 */
public interface Payload {
    default List<ValidationError> validate() {
        return ExternalPayloadValidatorProvider.get().validate(this);
    }
}
