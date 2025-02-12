package com.vluepixel.vetmanager.api.user.core.data;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;

import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Update user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateUserDataProvider {
    // -----------------------------------------------------------------------------------------------------------------
    // Users
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    public static final UpdateUserRequest VALID_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(2L)
            .firstName("New first name")
            .lastName("New last name")
            .build();

    public static final UserDto UPDATED_USER = UserDto.builder()
            .firstName(VALID_UPDATE_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_USER_REQUEST.getLastName())
            .build();

    // Role: ADMIN
    public static final UpdateUserRequest VALID_UPDATE_ADMIN_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("New first name")
            .lastName("New last name")
            .build();

    public static final UserDto UPDATED_ADMIN = UserDto.builder()
            .firstName(VALID_UPDATE_ADMIN_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_ADMIN_REQUEST.getLastName())
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // Role: ADMIN
    // - First name
    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_NULL = UpdateUserRequest.builder()
            .id(1L)
            .firstName(null)
            .lastName(ADMIN_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_EMPTY = UpdateUserRequest.builder()
            .id(1L)
            .firstName("")
            .lastName(ADMIN_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_BLANK = UpdateUserRequest.builder()
            .id(1L)
            .firstName(" ")
            .lastName(ADMIN_DTO.getLastName())
            .build();

    private final static int ADMIN_FIRST_NAME_MAX_LENGTH = 50;
    public static final UpdateUserRequest VALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH = UpdateUserRequest.builder()
            .id(1L)
            .firstName("a".repeat(ADMIN_FIRST_NAME_MAX_LENGTH))
            .lastName(ADMIN_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH = UpdateUserRequest
            .builder()
            .id(1L)
            .firstName("a".repeat(ADMIN_FIRST_NAME_MAX_LENGTH + 1))
            .lastName(ADMIN_DTO.getLastName())
            .build();

    // - Last name
    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_NULL = UpdateUserRequest.builder()
            .id(1L)
            .firstName(ADMIN_DTO.getFirstName())
            .lastName(null)
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_EMPTY = UpdateUserRequest.builder()
            .id(1L)
            .firstName(ADMIN_DTO.getFirstName())
            .lastName("")
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_BLANK = UpdateUserRequest.builder()
            .id(1L)
            .firstName(ADMIN_DTO.getFirstName())
            .lastName(" ")
            .build();

    private static final int ADMIN_LAST_NAME_MAX_LENGTH = 50;
    public static final UpdateUserRequest VALID_UPDATE_ADMIN_REQUEST_LAST_NAME_MAX_LENGTH = UpdateUserRequest.builder()
            .id(1L)
            .firstName(ADMIN_DTO.getFirstName())
            .lastName("a".repeat(ADMIN_LAST_NAME_MAX_LENGTH))
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_MAX_LENGTH = UpdateUserRequest
            .builder()
            .id(1L)
            .firstName(ADMIN_DTO.getFirstName())
            .lastName("a".repeat(ADMIN_LAST_NAME_MAX_LENGTH + 1))
            .build();

    // Role: USER
    // - First name
    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_FIRST_NAME_NULL = UpdateUserRequest.builder()
            .id(2L)
            .firstName(null)
            .lastName(USER_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_FIRST_NAME_EMPTY = UpdateUserRequest.builder()
            .id(2L)
            .firstName("")
            .lastName(USER_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_FIRST_NAME_BLANK = UpdateUserRequest.builder()
            .id(2L)
            .firstName(" ")
            .lastName(USER_DTO.getLastName())
            .build();

    private final static int USER_FIRST_NAME_MAX_LENGTH = 50;
    public static final UpdateUserRequest VALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH = UpdateUserRequest
            .builder()
            .id(2L)
            .firstName("a".repeat(USER_FIRST_NAME_MAX_LENGTH))
            .lastName(USER_DTO.getLastName())
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH = UpdateUserRequest
            .builder()
            .id(2L)
            .firstName("a".repeat(USER_FIRST_NAME_MAX_LENGTH + 1))
            .lastName(USER_DTO.getLastName())
            .build();

    // - Last name
    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_LAST_NAME_NULL = UpdateUserRequest.builder()
            .id(2L)
            .firstName(USER_DTO.getFirstName())
            .lastName(null)
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_LAST_NAME_EMPTY = UpdateUserRequest.builder()
            .id(2L)
            .firstName(USER_DTO.getFirstName())
            .lastName("")
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_LAST_NAME_BLANK = UpdateUserRequest.builder()
            .id(2L)
            .firstName(USER_DTO.getFirstName())
            .lastName(" ")
            .build();

    private static final int USER_LAST_NAME_MAX_LENGTH = 50;
    public static final UpdateUserRequest VALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH = UpdateUserRequest.builder()
            .id(2L)
            .firstName(USER_DTO.getFirstName())
            .lastName("a".repeat(USER_LAST_NAME_MAX_LENGTH))
            .build();

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH = UpdateUserRequest
            .builder()
            .id(2L)
            .firstName(USER_DTO.getFirstName())
            .lastName("a".repeat(USER_LAST_NAME_MAX_LENGTH + 1))
            .build();

}
