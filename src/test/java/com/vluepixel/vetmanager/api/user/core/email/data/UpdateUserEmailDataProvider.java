package com.vluepixel.vetmanager.api.user.core.email.data;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_TOO_LONG_REGISTER_USER_REQUEST;

import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserEmailRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Integration tests for the update user email functionality.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserEmailDataProvider {
    public static final UpdateUserEmailRequest INVALID_UPDATE_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(10L)
            .newEmail("new-email@new-email.com")
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Users
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    public static final UpdateUserEmailRequest VALID_UPDATE_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail("new-email@new-email.com")
            .build();

    public static final UserDto UPDATED_EMAIL_USER = UserDto
            .builder()
            .id(USER_DTO.getId())
            .email(VALID_UPDATE_USER_EMAIL_REQUEST.getNewEmail())
            .build();

    // - Invalid arguments

    // Email
    public static final UpdateUserEmailRequest INVALID_EMAIL_ALREADY_IN_USE_UPDATE_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(ADMIN_DTO.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_ITS_SAME_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(USER_DTO.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_TOO_LONG_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(INVALID_EMAIL_TOO_LONG_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_INVALID_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_BLANK_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_EMPTY_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_NULL_USER_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(USER_DTO.getId())
            .newEmail(INVALID_EMAIL_NULL_REGISTER_USER_REQUEST.getEmail())
            .build();

    // Role: ADMIN
    public static final UpdateUserEmailRequest VALID_UPDATE_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail("new-email@new-email.com")
            .build();

    public static final UserDto UPDATED_EMAIL_ADMIN = UserDto
            .builder()
            .id(ADMIN_DTO.getId())
            .email(VALID_UPDATE_USER_EMAIL_REQUEST.getNewEmail())
            .build();

    // - Invalid arguments

    // Email
    public static final UpdateUserEmailRequest INVALID_EMAIL_ALREADY_IN_USE_UPDATE_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(USER_DTO.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_ITS_SAME_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(ADMIN_DTO.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_TOO_LONG_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(INVALID_EMAIL_TOO_LONG_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_EMPTY_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST.getEmail())
            .build();

    public static final UpdateUserEmailRequest INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST = UpdateUserEmailRequest
            .builder()
            .id(ADMIN_DTO.getId())
            .newEmail(INVALID_EMAIL_NULL_REGISTER_USER_REQUEST.getEmail())
            .build();
}