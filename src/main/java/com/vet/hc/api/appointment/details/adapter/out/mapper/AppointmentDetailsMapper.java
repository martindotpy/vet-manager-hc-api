package com.vet.hc.api.appointment.details.adapter.out.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.appointment.details.adapter.out.persistence.entity.AppointmentDetailsEntity;
import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;

/**
 * Mapper for appointment details.
 */
@Mapper(componentModel = "spring")
public interface AppointmentDetailsMapper {
    /**
     * Maps the {@link AppointmentDetails} domain model to the
     * {@link AppointmentDetailsDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    AppointmentDetailsDto toDto(AppointmentDetails domain);

    /**
     * Maps the {@link AppointmentDetails} domain model to the
     * {@link AppointmentDetailsEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    AppointmentDetailsEntity toEntity(AppointmentDetails domain);

    /**
     * Maps the {@link AppointmentDetailsEntity} entity to the
     * {@link AppointmentDetails} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "appointment", ignore = true)
    AppointmentDetails toDomain(AppointmentDetailsEntity entity);
}
