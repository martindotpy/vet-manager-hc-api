package com.vet.hc.api.user.core.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.model.UserImpl;

/**
 * User mapper.
 *
 * <p>
 * Maps the {@link UserImpl} domain model to the {@link UserEntity} entity and
 * vice versa.
 * </p>
 *
 * <p>
 * Also maps the {@link UserImpl} domain model to the {@link UserDto} DTO.
 * </p>
 *
 * @see User
 * @see UserImpl
 * @see UserEntity
 * @see UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Maps the {@link UserEntity} entity to the {@link UserImpl} domain model.
     *
     * @param entity the entity to map
     * @return the domain model
     */
    UserImpl toDomain(UserEntity entity);

    /**
     * Maps the {@link UserImpl} domain model to the {@link UserEntity} entity.
     *
     * @param domain the domain model to map
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    UserEntity toEntity(User domain);

    /**
     * Maps the {@link UserImpl} domain model to the {@link UserDto} DTO.
     *
     * @param domain the domain model to map
     * @return the DTO
     */
    UserDto toDto(User domain);

    /**
     * Maps the {@link UserDto} DTO to the {@link UserImpl} domain model.
     *
     * @param dto the DTO to map
     * @return the domain model
     */
    @Mapping(target = "password", ignore = true)
    UserImpl toDomain(UserDto dto);
}
