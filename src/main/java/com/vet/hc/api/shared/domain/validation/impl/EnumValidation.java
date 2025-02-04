package com.vet.hc.api.shared.domain.validation.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.vet.hc.api.shared.domain.validation.Validation;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.RequiredArgsConstructor;

/**
 * Enum validation class.
 */
@RequiredArgsConstructor
public final class EnumValidation implements Validation {
    private final Class<? extends Enum<?>> enumType;
    private final String enumValue;
    private final String field;

    public static EnumValidation of(Class<? extends Enum<?>> enumType, String enumValue, String field) {
        return new EnumValidation(enumType, enumValue, field);
    }

    @Override
    public List<ValidationError> validate() {
        Objects.requireNonNull(enumType, "enumType must not be null");
        Objects.requireNonNull(enumValue, "enumValue must not be null");
        Objects.requireNonNull(field, "field must not be null");

        if (!Stream.of(enumType.getEnumConstants()).map(Enum::name).noneMatch(enumValue::equalsIgnoreCase)) {
            return List.of();
        }

        StringBuilder message = new StringBuilder();

        message.append("Valid values for `").append(field).append("` are: ");
        message.append(String.join(", ", Stream.of(enumType.getEnumConstants()).map(Enum::name).toList()));

        return List.of(new ValidationError(field, message.toString()));
    }
}
