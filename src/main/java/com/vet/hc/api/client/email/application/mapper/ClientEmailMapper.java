package com.vet.hc.api.client.email.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vet.hc.api.client.email.adapter.out.persistence.entity.ClientEmailEntity;
import com.vet.hc.api.client.email.domain.dto.ClientEmailDto;
import com.vet.hc.api.client.email.domain.model.ClientEmail;

@Mapper(componentModel = "spring")
public interface ClientEmailMapper {
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
