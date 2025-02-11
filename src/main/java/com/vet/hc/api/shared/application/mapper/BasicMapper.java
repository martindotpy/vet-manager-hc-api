package com.vet.hc.api.shared.application.mapper;

import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.failure.GenericFailure;

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
 * @param <DTO>    The DTO
 */
public interface BasicMapper<I, DTO, F extends Failure> {
    DTO toDto(I domain);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "UNEXPECTED")
    F toFailure(GenericFailure failure);

    default String trimString(String value) {
        return value != null ? value.trim() : null;
    }
}
