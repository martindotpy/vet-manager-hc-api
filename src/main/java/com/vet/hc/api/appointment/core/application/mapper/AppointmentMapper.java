package com.vet.hc.api.appointment.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.appointment.core.adapter.out.persistence.entity.AppointmentEntity;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;

/**
 * Mapper for appointment.
 */
@Mapper(uses = { AppointmentDetailsMapper.class, AppointmentTypeMapper.class })
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    /**
     * Maps the {@link Appointment} domain model to the
     * {@link AppointmentDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    AppointmentDto toDto(Appointment domain);

    /**
     * Maps the {@link Appointment} domain model to the
     * {@link AppointmentEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    AppointmentEntity toEntity(Appointment domain);

    /**
     * Maps the {@link AppointmentEntity} entity to the
     * {@link Appointment} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Appointment toDomain(AppointmentEntity entity);
}
