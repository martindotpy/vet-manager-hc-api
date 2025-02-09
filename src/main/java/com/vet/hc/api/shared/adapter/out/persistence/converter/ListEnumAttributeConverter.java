package com.vet.hc.api.shared.adapter.out.persistence.converter;

import java.util.List;

import jakarta.persistence.AttributeConverter;

/**
 * The interface List enum attribute converter.
 *
 * @param <T> the type parameter
 */
public interface ListEnumAttributeConverter<T extends Enum<T>> extends AttributeConverter<List<T>, String> {
}
