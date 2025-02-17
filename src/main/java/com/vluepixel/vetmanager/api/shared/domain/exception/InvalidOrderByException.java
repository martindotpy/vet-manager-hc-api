package com.vluepixel.vetmanager.api.shared.domain.exception;

import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

import lombok.Getter;

/**
 * Invalid order by field exception.
 */
@Getter
public final class InvalidOrderByException extends ValidationException {
    public InvalidOrderByException(Class<?> clazz) {
        super(List.of(
                new ValidationError(
                        "query.order_by",
                        "Solo los siguientes campos son válidos: " + Stream.of(clazz.getDeclaredFields())
                                .filter(field -> {
                                    Class<?> type = field.getType();

                                    return type.equals(String.class) ||
                                            type.equals(Integer.class) ||
                                            type.equals(Long.class) ||
                                            type.equals(Double.class) ||
                                            type.equals(Float.class) ||
                                            type.equals(Boolean.class) ||
                                            type.equals(LocalDate.class) ||
                                            type.equals(LocalDateTime.class) ||
                                            type.equals(int.class) ||
                                            type.equals(long.class) ||
                                            type.equals(double.class) ||
                                            type.equals(float.class) ||
                                            type.equals(boolean.class) ||
                                            type.equals(byte.class) ||
                                            type.equals(short.class) ||
                                            type.equals(char.class) ||
                                            type.equals(Byte.class) ||
                                            type.equals(Short.class) ||
                                            type.equals(Character.class) ||
                                            type.isEnum();
                                })
                                .map(field -> toSnakeCase(field.getName()))
                                .filter(field -> !"deleted".equals(field) ||
                                        !"password".equals(field)
                                        || field.contains("url"))
                                .reduce((a, b) -> a + ", " + b)
                                .get())));
    }
}
