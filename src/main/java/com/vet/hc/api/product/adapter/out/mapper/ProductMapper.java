package com.vet.hc.api.product.adapter.out.mapper;

import java.util.Collection;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.adapter.out.persistance.entity.CategoryEntity;
import com.vet.hc.api.product.adapter.out.persistance.entity.ProductEntity;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.shared.adapter.out.config.LazyLoadingAwareMapper;

/**
 * Mapper for products.
 */
@Mapper
public interface ProductMapper extends LazyLoadingAwareMapper {
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
    default boolean isNotLazyLoadedCategory(Collection<CategoryEntity> sourceCollection) {
        return isNotLazyLoaded(sourceCollection);
    }
}
