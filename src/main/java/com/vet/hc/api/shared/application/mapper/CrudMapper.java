package com.vet.hc.api.shared.application.mapper;

import com.vet.hc.api.shared.domain.failure.Failure;

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
public interface CrudMapper<I, DTO, F extends Failure, B>
        extends
        BasicMapper<I, DTO, F>,
        BuilderMapper<I, B> {
}
