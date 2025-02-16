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
 * @see User
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
     * Maps the {@link UserDto} DTO to the {@link User} domain entity.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>password</code></li>
     * <li><code>deleted</code></li>
     * <li><code>profileImageUrl</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param dto the user dto
     * @return the user entity
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    User toDomain(UserDto dto);

    /**
     * Create user from register user request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * <li><code>roles</code></li>
     * <li><code>deleted</code></li>
     * <li><code>password</code></li>
     * <li><code>profileImageUrl</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the register user request.
     * @return the user builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    User.UserBuilder fromRegister(RegisterUserRequest request);
}
