package com.vet.hc.api.appointment.type.adapter.out.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.appointment.type.adapter.out.persistence.entity.AppointmentTypeEntity;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

/**
 * Mapper for appointment type.
 */
@Mapper(componentModel = "spring")
public interface AppointmentTypeMapper {
    /**
     * Maps the {@link AppointmentType} domain model to the
     * {@link AppointmentTypeDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    AppointmentTypeDto toDto(AppointmentType domain);

    /**
     * Maps the {@link AppointmentType} domain model to the
     * {@link AppointmentTypeEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    AppointmentTypeEntity toEntity(AppointmentType domain);

    /**
     * Maps the {@link AppointmentTypeEntity} entity to the
     * {@link AppointmentType} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    AppointmentType toDomain(AppointmentTypeEntity entity);
}
