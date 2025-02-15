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
    public static final RegisterUserRequest VALID_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName("New")
            .lastName("User")
            .email("new-user@new-user.com")
            .password("password")
            .build();

    public static final UserDto VALID_REGISTER_USER_RESPONSE_CONTENT = UserDto
            .builder()
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
    public static final int MAX_FIRSTNAME_LENGTH = 50;
    public static final RegisterUserRequest INVALID_FIRSTNAME_TOOLONG_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName("a".repeat(MAX_FIRSTNAME_LENGTH + 1))
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName("a".repeat(MAX_FIRSTNAME_LENGTH))
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final UserDto VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT = UserDto
            .builder()
            .id(3L)
            .firstName("a".repeat(MAX_FIRSTNAME_LENGTH))
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .roles(List.of(UserRole.USER))
            .profileImageUrl(null)
            .build();

    public static final RegisterUserRequest INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(" ")
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName("")
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(null)
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - LastName
    private static final int MAX_LASTNAME_LENGTH = 50;
    public static final RegisterUserRequest INVALID_LASTNAME_TOOLONG_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("a".repeat(MAX_LASTNAME_LENGTH + 1))
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("a".repeat(MAX_LASTNAME_LENGTH))
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final UserDto VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT = UserDto
            .builder()
            .id(3L)
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("a".repeat(MAX_LASTNAME_LENGTH))
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .roles(List.of(UserRole.USER))
            .profileImageUrl(null)
            .build();

    public static final RegisterUserRequest INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(" ")
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName("")
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(null)
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - Email
    private static final int MAX_EMAIL_LENGTH = 254;
    private static final String DOMAIN = "@hotmail.com";

    public static String generateEmail(int repeats) {
        int nameLength = repeats - DOMAIN.length();
        return "a".repeat(nameLength) + DOMAIN;
    }

    public static final RegisterUserRequest INVALID_EMAIL_TOOLONG_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(generateEmail(MAX_EMAIL_LENGTH + 1))
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email("invalid")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(" ")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(" ")
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    public static final RegisterUserRequest INVALID_EMAIL_NULL_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(null)
            .password(VALID_REGISTER_USER_REQUEST.getPassword())
            .build();

    // - Password
    private static final int MAX_PASSWORD_LENGTH = 60;
    private static final int MIN_PASSWORD_LENGTH = 8;
    public static final RegisterUserRequest INVALID_PASSWORD_TOOLONG_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MAX_PASSWORD_LENGTH + 1))
            .build();

    public static final RegisterUserRequest VALID_PASSWORD_MAX_LENGTH_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MAX_PASSWORD_LENGTH))
            .build();

    public static final RegisterUserRequest INVALID_PASSWORD_TOOSHORT_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MIN_PASSWORD_LENGTH - 1))
            .build();

    public static final RegisterUserRequest VALID_PASSWORD_MIN_LENGTH_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password("a".repeat(MIN_PASSWORD_LENGTH))
            .build();

    public static final RegisterUserRequest INVALID_PASSWORD_BLANK_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(" ")
            .build();

    public static final RegisterUserRequest INVALID_PASSWORD_EMPTY_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(" ")
            .build();

    public static final RegisterUserRequest INVALID_PASSWORD_NULL_REGISTER_USER_REQUEST = RegisterUserRequest
            .builder()
            .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
            .email(VALID_REGISTER_USER_REQUEST.getEmail())
            .password(null)
            .build();
}
