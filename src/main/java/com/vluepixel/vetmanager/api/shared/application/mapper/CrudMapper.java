package com.vluepixel.vetmanager.api.shared.application.mapper;

/**
 * Crud mapper.
 *
 * @param <E>   The domain entity.
 * @param <DTO> The DTO.
 * @param <B>   The builder.
 *
 * @see BasicMapper
 * @see BuilderMapper
 */
public interface CrudMapper<E, DTO, B>
        extends
        BasicMapper<E, DTO>,
        BuilderMapper<E, B> {
}
