package com.vet.hc.api.patient.species.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.patient.species.adapter.out.persistence.entity.SpeciesEntity;
import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.model.Species;

/**
 * Mapper for species.
 */
@Mapper
public interface SpeciesMapper {
    SpeciesMapper INSTANCE = Mappers.getMapper(SpeciesMapper.class);

    /**
     * Maps the {@link Species} domain model to the
     * {@link SpeciesDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    SpeciesDto toDto(Species domain);

    /**
     * Maps the {@link Species} domain model to the
     * {@link SpeciesEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    SpeciesEntity toEntity(Species domain);

    /**
     * Maps the {@link SpeciesEntity} entity to the
     * {@link Species} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Species toDomain(SpeciesEntity entity);
}
