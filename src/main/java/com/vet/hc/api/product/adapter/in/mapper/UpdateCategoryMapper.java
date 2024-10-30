package com.vet.hc.api.product.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.adapter.in.request.UpdateCategoryRequest;
import com.vet.hc.api.product.domain.command.UpdateCategoryCommand;

/**
 * Mapper for updating a category.
 */
@Mapper
public interface UpdateCategoryMapper {
    UpdateCategoryMapper INSTANCE = Mappers.getMapper(UpdateCategoryMapper.class);

    /**
     * Maps a {@link UpdateCategoryRequest} to a {@link UpdateCategoryCommand}.
     *
     * @param request the request to map.
     * @return the mapped command
     */
    UpdateCategoryCommand toCommand(UpdateCategoryRequest request);
}