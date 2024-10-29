package com.vet.hc.api.client.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.out.persistance.entity.ClientEmailEntity;
import com.vet.hc.api.client.application.dto.ClientEmailDto;
import com.vet.hc.api.client.domain.model.ClientEmail;

@Mapper
public interface ClientEmailMapper {
    ClientEmailMapper INSTANCE = Mappers.getMapper(ClientEmailMapper.class);

    /**
     * Maps the {@link ClientEmail} domain model to the {@link ClientEmailDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    ClientEmailDto toDto(ClientEmail domain);

    /**
     * Maps the {@link ClientEmailDto} DTO to the {@link ClientEmail} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    ClientEmailEntity toEntity(ClientEmail domain);

    /**
     * Maps the {@link ClientEmailEntity} entity to the {@link ClientEmail} domain
     * model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "client", ignore = true)
    ClientEmail toDomain(ClientEmailEntity entity);

    /**
     * Maps the {@link ClientEmailDto} DTO to the {@link ClientEmail} domain model.
     *
     * @param dto the DTO to map.
     * @return the domain model
     */
    @Mapping(target = "client", ignore = true)
    ClientEmail toDomain(ClientEmailDto dto);
}
