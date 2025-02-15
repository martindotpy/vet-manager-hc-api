package com.vluepixel.vetmanager.api.shared.application.mapper;

/**
 * Builder mapper.
 *
 * @param <T> The type.
 * @param <B> The builder.
 */
public interface BuilderMapper<T, B> {
    /**
     * Map the type to the builder.
     *
     * @param t The type.
     * @return The builder
     */
    B toBuilder(T t);
}
