package com.vluepixel.vetmanager.api.shared.application.mapper;

/**
 * Crud mapper.
 *
 * @param <I>    The domain interface
 * @param <Impl> The domain implementation
 * @param <E>    The entity
 * @param <DTO>  The DTO
 * @param <F>    The failure
 * @param <CP>   The create request
 * @param <UP>   The update request
 * @param <B>    The builder
 *
 * @see BasicMapper
 * @see CreateRequestMapper
 * @see UpdateRequestMapper
 * @see BuilderMapper
 */
public interface CrudMapper<I, DTO, B>
        extends
        BasicMapper<I, DTO>,
        BuilderMapper<I, B> {
}
