package com.vluepixel.vetmanager.api.shared.application.mapper;

/**
 * Crud mapper.
 *
 * @param <I>    The domain interface
 * @param <Impl> The domain implementation
 * @param <E>    The entity
 * @param <DTO>  The DTO
 * @param <F>    The failure
 * @param <CP>   The create payload
 * @param <UP>   The update payload
 * @param <B>    The builder
 *
 * @see BasicMapper
 * @see CreatePayloadMapper
 * @see UpdatePayloadMapper
 * @see BuilderMapper
 */
public interface CrudMapper<I, DTO, B>
        extends
        BasicMapper<I, DTO>,
        BuilderMapper<I, B> {
}
