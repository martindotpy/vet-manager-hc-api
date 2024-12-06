package com.vet.hc.api.bill.productsale.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vet.hc.api.bill.productsale.adapter.out.persistence.entity.ProductSaleEntity;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for product sale.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProductSaleMapper {
    /**
     * Maps the {@link ProductSale} domain model to the
     * {@link ProductSaleDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    ProductSaleDto toDto(ProductSale domain);

    /**
     * Maps the {@link ProductSale} domain model to the
     * {@link ProductSaleEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    ProductSaleEntity toEntity(ProductSale domain);

    /**
     * Maps the {@link ProductSaleEntity} entity to the
     * {@link ProductSale} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "bill", ignore = true)
    ProductSale toDomain(ProductSaleEntity entity);
}
