package com.vet.hc.api.patient.race.adapter.out.mapper;

import org.mapstruct.Mapper;

import com.vet.hc.api.patient.race.adapter.out.persistence.entity.RaceEntity;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.model.Race;

/**
 * Mapper for race.
 */
@Mapper(componentModel = "spring")
public interface RaceMapper {
    /**
     * Maps the {@link Race} domain model to the
     * {@link RaceDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    RaceDto toDto(Race domain);

    /**
     * Maps the {@link Race} domain model to the
     * {@link RaceEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    RaceEntity toEntity(Race domain);

    /**
     * Maps the {@link RaceEntity} entity to the
     * {@link Race} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Race toDomain(RaceEntity entity);
}
