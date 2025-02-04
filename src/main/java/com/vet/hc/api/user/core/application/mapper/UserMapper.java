package com.vet.hc.api.user.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.image.core.application.mapper.ImageMapper;
import com.vet.hc.api.shared.application.mapper.BasicMapper;
import com.vet.hc.api.user.core.adapter.out.persistence.entity.UserEntity;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
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
public interface UserMapper extends BasicMapper<User, UserImpl, UserEntity, UserDto, UserFailure> {
    /**
     * Creates a new {@link UserImpl} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default UserImpl.UserImplBuilder createBuilder() {
        return UserImpl.builder();
    }

    /**
     * Maps the byte array of the profile image to a string.
     *
     * @param value the byte array to map.
     * @return the string
     */
    default String mapImageProfileDataToImageProfileData(byte[] value) {
        return ImageMapper.INSTANCE.mapDataToData(value);
    }

    /**
     * Maps the {@link UserDto} DTO to the {@link UserImpl} domain model.
     *
     * @param dto the DTO to map
     * @return the domain model
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "profileImage", ignore = true)
    UserImpl toDomain(UserDto dto);

    /**
     * Maps the {@link RegisterUserPayload} payload to the {@link UserImpl} domain
     * model.
     *
     * @param payload the payload to map
     * @return the domain model
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileImage", ignore = true)
    UserImpl.UserImplBuilder fromRegister(RegisterUserPayload payload);
}
