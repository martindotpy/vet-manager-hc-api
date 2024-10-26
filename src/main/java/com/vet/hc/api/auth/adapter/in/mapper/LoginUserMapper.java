package com.vet.hc.api.auth.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.auth.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.domain.command.LoginUserCommand;

/**
 * Mapper for the login user command.
 */
@Mapper
public interface LoginUserMapper {
    LoginUserMapper INSTANCE = Mappers.getMapper(LoginUserMapper.class);

    /**
     * Maps a login request to a login user command.
     *
     * @param request The login request.
     * @return The login user command
     */
    LoginUserCommand toCommand(LoginUserRequest request);
}
