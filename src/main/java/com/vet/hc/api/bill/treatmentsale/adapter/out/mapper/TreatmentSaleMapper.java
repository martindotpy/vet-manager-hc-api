package com.vet.hc.api.bill.treatmentsale.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.entity.TreatmentSaleEntity;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for treatment sale.
 */
@Mapper(uses = { UserMapper.class })
public interface TreatmentSaleMapper {
    TreatmentSaleMapper INSTANCE = Mappers.getMapper(TreatmentSaleMapper.class);

    /**
     * Maps the {@link TreatmentSale} domain model to the
     * {@link TreatmentSaleDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    TreatmentSaleDto toDto(TreatmentSale domain);

    /**
     * Maps the {@link TreatmentSale} domain model to the
     * {@link TreatmentSaleEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    TreatmentSaleEntity toEntity(TreatmentSale domain);

    /**
     * Maps the {@link TreatmentSaleEntity} entity to the
     * {@link TreatmentSale} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "bill", ignore = true)
    TreatmentSale toDomain(TreatmentSaleEntity entity);
}
