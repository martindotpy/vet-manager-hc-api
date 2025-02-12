package com.vluepixel.vetmanager.api.shared.domain.validation;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationRequest implements Validation {
    private final Request request;

    public static ValidationRequest of(Request request) {
        return new ValidationRequest(request);
    }

    @Override
    public List<ValidationError> validate() {
        return request.validate();
    }
}
