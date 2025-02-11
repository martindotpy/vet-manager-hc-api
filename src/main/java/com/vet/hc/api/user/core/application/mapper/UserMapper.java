package com.vet.hc.api.user.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.application.mapper.CrudMapper;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;

/**
 * User mapper.
 *
 * <p>
 * Maps the {@link User} domain model to the {@link UserEntity} entity and
 * vice versa.
 * </p>
 *
 * <p>
 * Also maps the {@link User} domain model to the {@link UserDto} DTO.
 * </p>
 *
 * @see User
 * @see User
 * @see UserEntity
 * @see UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper
        extends CrudMapper<User, UserDto, UserFailure, User.UserBuilder> {
    /**
     * Creates a new {@link User} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default User.UserBuilder createBuilder() {
        return User.builder();
    }

    /**
     * Maps the {@link UserDto} DTO to the {@link User} domain model.
     *
     * @param dto the DTO to map
     * @return the domain model
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    User toDomain(UserDto dto);

    /**
     * Maps the {@link RegisterUserPayload} payload to the {@link User} domain
     * model.
     *
     * @param payload the payload to map
     * @return the domain model
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    User.UserBuilder fromRegister(RegisterUserPayload payload);
}
