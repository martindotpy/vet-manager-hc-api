package com.vet.hc.api.client.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.client.adapter.out.persistance.entity.ClientPhoneEntity;
import com.vet.hc.api.client.domain.dto.ClientPhoneDto;
import com.vet.hc.api.client.domain.model.ClientPhone;

@Mapper
public interface ClientPhoneMapper {
    ClientPhoneMapper INSTANCE = Mappers.getMapper(ClientPhoneMapper.class);

    /**
     * Maps the {@link ClientPhone} domain model to the {@link ClientPhoneDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    ClientPhoneDto toDto(ClientPhone domain);

    /**
     * Maps the {@link ClientPhoneDto} DTO to the {@link ClientPhone} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    ClientPhoneEntity toEntity(ClientPhone domain);

    /**
     * Maps the {@link ClientPhoneEntity} entity to the {@link ClientPhone} domain
     * model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "client", ignore = true)
    ClientPhone toDomain(ClientPhoneEntity entity);

    /**
     * Maps the {@link ClientPhoneDto} DTO to the {@link ClientPhone} domain model.
     *
     * @param dto the DTO to map.
     * @return the domain model
     */
    @Mapping(target = "client", ignore = true)
    ClientPhone toDomain(ClientPhoneDto dto);
}
