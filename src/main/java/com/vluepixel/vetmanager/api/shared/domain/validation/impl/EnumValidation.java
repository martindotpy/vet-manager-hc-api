package com.vluepixel.vetmanager.api.shared.domain.validation.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import com.vluepixel.vetmanager.api.shared.domain.validation.Validation;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

import lombok.RequiredArgsConstructor;

/**
 * Enum validation class.
 */
@RequiredArgsConstructor
public final class EnumValidation<T extends Enum<?>> implements Validation {
    private final Class<T> enumType;
    private final String enumValue;
    private final Function<T, String> converter;
    private final String field;

    public static <T extends Enum<?>> EnumValidation<T> of(Class<T> enumType, String enumValue, String field) {
        return new EnumValidation<T>(enumType, enumValue, Enum::name, field);
    }

    public static <T extends Enum<?>> EnumValidation<T> of(
            Class<T> enumType,
            String enumValue,
            Function<T, String> converter,
            String field) {
        return new EnumValidation<T>(enumType, enumValue, converter, field);
    }

    @Override
    public List<ValidationError> validate() {
        Objects.requireNonNull(enumType, "enumType must not be null");
        Objects.requireNonNull(enumValue, "enumValue must not be null");
        Objects.requireNonNull(field, "field must not be null");

        if (!Stream.of(enumType.getEnumConstants()).map(converter).noneMatch(enumValue::equalsIgnoreCase)) {
            return List.of();
        }

        StringBuilder message = new StringBuilder();

        message.append("Valid values for `").append(field).append("` are: ");
        message.append(String.join(", ", Stream.of(enumType.getEnumConstants()).map(Enum::name).toList()));

        return List.of(new ValidationError(field, message.toString()));
    }
}
