package com.vet.hc.api.user.core.data;

import com.vet.hc.api.user.core.adapter.in.request.UpdateUserRequest;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.payload.UpdateUserPayload;

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

    // - Role: USER
    public static final UpdateUserPayload VALID_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("New first name")
            .lastName("New last name")
            .build();

    public static final UserDto UPDATED_USER = UserDto.builder()
            .firstName(VALID_UPDATE_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_USER_REQUEST.getLastName())
            .build();

    // - Role: ADMIN
    public static final UpdateUserPayload VALID_UPDATE_ADMIN_REQUEST = UpdateUserRequest.builder()
            .id(2L)
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

    // - First name
    public static final UpdateUserPayload BLANK_FIRST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName(" ")
            .lastName("New last name")
            .build();

    public static final UpdateUserPayload NULL_FIRST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName(null)
            .lastName("New last name")
            .build();

    public static final UpdateUserPayload TOO_LONG_FIRST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("a".repeat(51))
            .lastName("New last name")
            .build();

    // - Last name
    public static final UpdateUserPayload BLANK_LAST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("New first name")
            .lastName(" ")
            .build();

    public static final UpdateUserPayload NULL_LAST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("New first name")
            .lastName(null)
            .build();

    public static final UpdateUserPayload TOO_LONG_LAST_NAME_UPDATE_USER_REQUEST = UpdateUserRequest.builder()
            .id(1L)
            .firstName("New first name")
            .lastName("a".repeat(51))
            .build();

}
