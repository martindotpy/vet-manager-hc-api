package com.vluepixel.vetmanager.api.shared.application.mapper;

/**
 * Basic mapper.
 *
 * @param <E>   The domain entity.
 * @param <DTO> The DTO.
 */
public interface BasicMapper<E, DTO> {
    /**
     * Maps the domain entity to the DTO.
     *
     * @param domain the domain entity.
     * @return the DTO
     */
    DTO toDto(E domain);
}
