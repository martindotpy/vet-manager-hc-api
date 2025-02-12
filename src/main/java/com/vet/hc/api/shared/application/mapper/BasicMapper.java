package com.vet.hc.api.shared.application.mapper;

/**
 * Basic mapper.
 *
 * <p>
 * Maps the domain interface to the domain implementation, the entity and the
 * DTO.
 * </p>
 *
 * @param <I>    The domain interface
 * @param <Impl> The domain implementation
 * @param <E>    The entity
 * @param <DTO>  The DTO
 */
public interface BasicMapper<I, DTO> {
    DTO toDto(I domain);

    default String trimString(String value) {
        return value != null ? value.trim() : null;
    }
}
