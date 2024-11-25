package com.vet.hc.api.bill.appointmentsale.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.entity.AppointmentSaleEntity;
import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for appointment sale.
 */
@Mapper(uses = { UserMapper.class })
public interface AppointmentSaleMapper {
    AppointmentSaleMapper INSTANCE = Mappers.getMapper(AppointmentSaleMapper.class);

    /**
     * Maps the {@link AppointmentSale} domain model to the
     * {@link AppointmentSaleDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    AppointmentSaleDto toDto(AppointmentSale domain);

    /**
     * Maps the {@link AppointmentSale} domain model to the
     * {@link AppointmentSaleEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    AppointmentSaleEntity toEntity(AppointmentSale domain);

    /**
     * Maps the {@link AppointmentSaleEntity} entity to the
     * {@link AppointmentSale} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "bill", ignore = true)
    AppointmentSale toDomain(AppointmentSaleEntity entity);
}