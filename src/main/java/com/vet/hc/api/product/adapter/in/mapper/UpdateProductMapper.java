package com.vet.hc.api.product.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.adapter.in.request.UpdateProductRequest;
import com.vet.hc.api.product.domain.command.UpdateProductCommand;

/**
 * Mapper for updating a product.
 */
@Mapper
public interface UpdateProductMapper {
    UpdateProductMapper INSTANCE = Mappers.getMapper(UpdateProductMapper.class);

    /**
     * Maps a {@link UpdateProductRequest} to a {@link UpdateProductCommand}.
     *
     * @param request the request to map.
     * @return the mapped command
     */
    UpdateProductCommand toCommand(UpdateProductRequest request);
}