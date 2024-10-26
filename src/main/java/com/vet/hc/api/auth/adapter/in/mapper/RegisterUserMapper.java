package com.vet.hc.api.auth.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.auth.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;

/**
 * Mapper for the register user command.
 */
@Mapper
public interface RegisterUserMapper {
    RegisterUserMapper INSTANCE = Mappers.getMapper(RegisterUserMapper.class);

    /**
     * Maps a register request to a register user command.
     *
     * @param request The register request.
     * @return The register user command
     */
    @Mapping(target = "role", constant = "USER")
    RegisterUserCommand toCommand(RegisterUserRequest request);
}
