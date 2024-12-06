package com.vet.hc.api.client.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vet.hc.api.client.core.adapter.out.persistence.entity.ClientEntity;
import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.client.core.domain.model.FullDataClient;
import com.vet.hc.api.client.email.application.mapper.ClientEmailMapper;
import com.vet.hc.api.client.phone.application.mapper.ClientPhoneMapper;

/**
 * Mapper for clients.
 */
@Mapper(componentModel = "spring", uses = { ClientEmailMapper.class, ClientPhoneMapper.class })
public interface ClientMapper {
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
    ClientEntity toEntity(Client domain);

    /**
     * Maps the {@link ClientEntity} entity to the {@link Client} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Client toDomain(ClientEntity entity);

    /**
     * Maps the {@link ClientDto} DTO to the {@link Client} domain model.
     *
     * @param dto the DTO to map.
     * @return the domain model
     */
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "phones", ignore = true)
    Client toDomain(ClientDto dto);

    /**
     * Maps the {@link FullDataClient} domain model to the {@link FullDataClientDto}
     * DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    FullDataClientDto toDto(FullDataClient domain);
}
