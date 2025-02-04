package com.vet.hc.api.shared.adapter.in.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.vet.hc.api.shared.adapter.in.exception.InvalidEnumValueException;

/**
 * Converter class for converting a string to an enum.
 */
public class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {
    private final Class<T> enumType;

    public StringToEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(@NonNull String source) {
        try {
            return Enum.valueOf(enumType, source.toUpperCase());
        } catch (Exception e) {
            throw new InvalidEnumValueException(enumType);
        }
    }
}
