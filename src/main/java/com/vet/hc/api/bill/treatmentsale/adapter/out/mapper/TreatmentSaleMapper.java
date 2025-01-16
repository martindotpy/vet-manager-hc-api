package com.vet.hc.api.bill.treatmentsale.adapter.out.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.entity.TreatmentSaleEntity;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for treatment sale.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TreatmentSaleMapper {
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
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
