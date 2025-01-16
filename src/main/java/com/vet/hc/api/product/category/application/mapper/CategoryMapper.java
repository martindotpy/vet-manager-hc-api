package com.vet.hc.api.product.category.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.product.category.adapter.out.persistence.entity.CategoryEntity;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.model.Category;

/**
 * Mapper for categories.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    /**
     * Maps the {@link Category} domain model to the {@link CategoryDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    CategoryDto toDto(Category domain);

    /**
     * Maps the {@link CategoryDto} DTO to the {@link Category} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CategoryEntity toEntity(Category domain);

    /**
     * Maps the {@link CategoryEntity} entity to the {@link Category} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Category toDomain(CategoryEntity entity);

    /**
     * Maps the {@link CategoryDto} DTO to the {@link Category} domain model.
     *
     * @param dto the DTO to map.
     * @return the domain model
     */
    Category toDomain(CategoryDto dto);
}
