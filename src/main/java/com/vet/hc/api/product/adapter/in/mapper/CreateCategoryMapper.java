package com.vet.hc.api.product.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.adapter.in.request.CreateCategoryRequest;
import com.vet.hc.api.product.domain.command.CreateCategoryCommand;

/**
 * Mapper for creating a category.
 */
@Mapper
public interface CreateCategoryMapper {
    CreateCategoryMapper INSTANCE = Mappers.getMapper(CreateCategoryMapper.class);

    /**
     * Maps a {@link CreateCategoryRequest} to a {@link CreateCategoryCommand}.
     *
     * @param request the request to map.
     * @return the mapped command
     */
    CreateCategoryCommand toCommand(CreateCategoryRequest request);
}
