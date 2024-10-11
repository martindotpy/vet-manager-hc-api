package com.vet.hc.api.user.adapter.out.mapper;

import org.mapstruct.Mapper;

import com.vet.hc.api.user.adapter.out.persistance.model.UserEntity;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.model.User;

/**
 * User mapper.
 *
 * <p>
 * Maps the {@link User} domain model to the {@link UserDto} DTO.
 * </p>
 *
 * @see User
 * @see UserEntity
 * @see UserDto
 */
@Mapper
public interface UserMapper {
    /**
     * Maps the {@link User} domain model to the {@link UserDto} DTO.
     *
     * @param domain the domain model to map
     * @return the DTO
     */
    UserDto domainToDto(User domain);
}
