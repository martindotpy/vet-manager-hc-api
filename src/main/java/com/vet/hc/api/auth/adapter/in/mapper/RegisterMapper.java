package com.vet.hc.api.auth.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.auth.adapter.in.request.RegisterRequest;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;

/**
 * Mapper for the register user command.
 */
@Mapper
public interface RegisterMapper {
    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    /**
     * Maps a register request to a register user command.
     *
     * @param request The register request.
     * @return The register user command
     */
    @Mapping(target = "role", constant = "USER")
    RegisterUserCommand toCommand(RegisterRequest request);
}
