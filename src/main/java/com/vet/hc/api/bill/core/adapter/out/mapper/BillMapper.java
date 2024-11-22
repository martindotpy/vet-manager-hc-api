package com.vet.hc.api.bill.core.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.model.Bill;

/**
 * Mapper for appointment type.
 */
@Mapper
public interface BillMapper {
    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    /**
     * Maps the {@link Bill} domain model to the
     * {@link BillDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    BillDto toDto(Bill domain);

    /**
     * Maps the {@link Bill} domain model to the
     * {@link BillEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    BillEntity toEntity(Bill domain);

    /**
     * Maps the {@link BillEntity} entity to the
     * {@link Bill} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Bill toDomain(BillEntity entity);
}
