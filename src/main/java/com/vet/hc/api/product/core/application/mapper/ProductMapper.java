package com.vet.hc.api.product.core.application.mapper;

import java.util.Collection;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.category.adapter.out.persistence.entity.CategoryEntity;
import com.vet.hc.api.product.core.adapter.out.persistence.entity.ProductEntity;
import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.shared.adapter.out.config.LazyFindingAwareMapper;

/**
 * Mapper for products.
 */
@Mapper
public interface ProductMapper extends LazyFindingAwareMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Maps the {@link Product} domain model to the {@link ProductDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    ProductDto toDto(Product domain);

    /**
     * Maps the {@link ProductDto} DTO to the {@link Product} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    ProductEntity toEntity(Product domain);

    /**
     * Maps the {@link ProductEntity} entity to the {@link Product} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Product toDomain(ProductEntity entity);

    /**
     * Maps the {@link ProductDto} DTO to the {@link Product} domain model.
     *
     * @param dto the DTO to map.
     * @return the domain model
     */
    @Mapping(target = "categories", ignore = true)
    Product toDomain(ProductDto dto);

    /**
     * Condition to check if the categories are not lazy loaded.
     */
    @Condition
    default boolean isNotLazyFindedCategory(Collection<CategoryEntity> sourceCollection) {
        return isNotLazyFinded(sourceCollection);
    }
}
