package com.vet.hc.api.user.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;

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
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps the {@link User} domain model to the {@link UserDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    UserDto toDto(User domain);

    /**
     * Maps the {@link UserDto} DTO to the {@link User} domain model.
     *
     * @param domain the DTO to map.
     * @return the domain model
     */
    UserEntity toEntity(User domain);

    /**
     * Maps the {@link UserEntity} entity to the {@link User} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    User toDomain(UserEntity entity);
}
