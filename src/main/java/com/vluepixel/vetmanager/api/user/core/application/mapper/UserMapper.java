package com.vluepixel.vetmanager.api.user.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.auth.core.domain.request.RegisterUserRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

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
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface UserMapper
        extends CrudMapper<User, UserDto, User.UserBuilder> {
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
     * Maps the {@link RegisterUserRequest} request to the {@link User} domain
     * model.
     *
     * @param request the request to map
     * @return the domain model
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    User.UserBuilder fromRegister(RegisterUserRequest request);
}
