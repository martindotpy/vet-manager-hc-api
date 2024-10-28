package com.vet.hc.api.product.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.product.adapter.in.request.CreateProductRequest;
import com.vet.hc.api.product.domain.command.CreateProductCommand;

/**
 * Mapper for creating a product.
 */
@Mapper
public interface CreateProductMapper {
    CreateProductMapper INSTANCE = Mappers.getMapper(CreateProductMapper.class);

    /**
     * Maps a {@link CreateProductRequest} to a {@link CreateProductCommand}.
     *
     * @param request the request to map.
     * @return the mapped command
     */
    CreateProductCommand toCommand(CreateProductRequest request);
}
