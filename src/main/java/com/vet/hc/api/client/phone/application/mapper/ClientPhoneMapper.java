package com.vet.hc.api.client.phone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vet.hc.api.client.phone.adapter.out.persistence.entity.ClientPhoneEntity;
import com.vet.hc.api.client.phone.domain.dto.ClientPhoneDto;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;

@Mapper(componentModel = "spring")
public interface ClientPhoneMapper {
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
