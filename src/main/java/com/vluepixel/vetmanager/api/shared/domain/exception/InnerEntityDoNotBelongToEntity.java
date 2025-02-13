package com.vluepixel.vetmanager.api.shared.domain.exception;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

/**
 * Inner entity do not belong to entity exception.
 */
public class InnerEntityDoNotBelongToEntity extends ValidationException {
    public InnerEntityDoNotBelongToEntity(String field, String message) {
        super(List.of(new ValidationError(field, message)));
    }
}
