package com.vet.hc.api.shared.application.mapper;

/**
 * Builder mapper.
 *
 * @param <T> The type to map from.
 * @param <B> The builder type to map to.
 */
public interface BuilderMapper<T, B> {
    /**
     * Map from builder to type.
     *
     * @param b The builder.
     * @return The type
     */
    B toBuilder(T t);
}
