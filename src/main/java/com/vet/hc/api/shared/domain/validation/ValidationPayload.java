package com.vet.hc.api.shared.domain.validation;

import java.util.List;

import com.vet.hc.api.shared.domain.payload.Payload;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationPayload implements Validation {
    private final Payload payload;

    public static ValidationPayload of(Payload payload) {
        return new ValidationPayload(payload);
    }

    @Override
    public List<ValidationError> validate() {
        return payload.validate();
    }
}
