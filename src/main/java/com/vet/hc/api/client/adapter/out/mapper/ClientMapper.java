package com.vet.hc.api.client.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.out.persistance.entity.ClientEntity;
import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.model.FullDataClient;

/**
 * Mapper for clients.
 */
@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    /**
     * Maps the {@link Client} domain model to the {@link ClientDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    ClientDto toDto(Client domain);

    /**
     * Maps the {@link ClientDto} DTO to the {@link Client} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "phones", ignore = true)
    ClientEntity toEntity(Client domain);

    /**
     * Maps the {@link ClientEntity} entity to the {@link Client} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Client toDomain(ClientEntity entity);

    FullDataClientDto toDto(FullDataClient domain);
}