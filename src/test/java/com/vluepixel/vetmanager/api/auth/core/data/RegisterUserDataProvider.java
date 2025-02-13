package com.vluepixel.vetmanager.api.auth.core.data;

import java.util.List;

import com.vluepixel.vetmanager.api.auth.core.domain.request.RegisterUserRequest;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.domain.model.enums.UserRole;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Register user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserDataProvider {
    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST = RegisterUserRequest.builder()
            .firstName("New")
            .lastName("User")
            .email("new-user@new-user.com")
            .password("password")
            .build();

    public static final UserDto VALID_REGISTER_USER_RESPONSE_CONTENT = UserDto.builder()
            .id(3L)
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .roles(List.of(UserRole.USER))
            .profileImageUrl(null)
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------------------

    // - First name
    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_FIRST_NAME_NULL = RegisterUserRequest
            .builder()
            .firstName(null)
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_FIRST_NAME_EMPTY = RegisterUserRequest
            .builder()
            .firstName("")
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_FIRST_NAME_BLANK = RegisterUserRequest
            .builder()
            .firstName(" ")
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    private static final int MAX_FIRST_NAME_LENGTH = 50;
    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName("a".repeat(MAX_FIRST_NAME_LENGTH))
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName("a".repeat(MAX_FIRST_NAME_LENGTH + 1))
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - Last name
    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_LAST_NAME_NULL = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(null)
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_LAST_NAME_EMPTY = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("")
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_LAST_NAME_BLANK = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(" ")
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    private static final int MAX_LAST_NAME_LENGTH = 50;
    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("a".repeat(MAX_LAST_NAME_LENGTH + 1))
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("a".repeat(MAX_LAST_NAME_LENGTH))
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - Email
    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_EMAIL_NULL = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(null)
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_EMAIL_EMPTY = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email("")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_EMAIL_BLANK = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(" ")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_EMAIL_INVALID = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email("invalid-email")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - Password
    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_PASSWORD_NULL = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(null)
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_PASSWORD_EMPTY = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("")
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_PASSWORD_BLANK = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(" ")
            .build();

    private static final int MIN_PASSWORD_LENGTH = 8;
    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MIN_PASSWORD_LENGTH))
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MIN_PASSWORD_LENGTH - 1))
            .build();

    private static final int MAX_PASSWORD_LENGTH = 60;
    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MAX_PASSWORD_LENGTH))
            .build();

    public static final RegisterUserRequest INVALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MAX_PASSWORD_LENGTH + 1))
            .build();
}
