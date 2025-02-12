package com.vluepixel.vetmanager.api.auth.core.data;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_JWT;

import com.vluepixel.vetmanager.api.auth.core.adapter.in.request.LoginUserRequest;
import com.vluepixel.vetmanager.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.domain.payload.LoginUserPayload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Login user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserDataProvider {
    public static final LoginUserPayload VALID_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email("user@user.com")
            .password("user")
            .build();

    public static final AuthenticationResponse VALID_LOGIN_USER_RESPONSE = AuthenticationResponse.builder()
            .message("Usuario " + VALID_LOGIN_USER_REQUEST.getEmail() + " ha ingresado correctamente")
            .content(JwtDto.builder().jwt(USER_JWT).build())
            .build();

    // - Invalid credentials
    public static final LoginUserPayload WRONG_PASSWORD_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email(VALID_LOGIN_USER_REQUEST.getEmail())
            .password("invalid")
            .build();

    public static final LoginUserPayload WRONG_EMAIL_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email("invalid@invalid.com")
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------------------

    // - Email
    public static final LoginUserPayload NULL_EMAIL_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email(null)
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .build();

    public static final LoginUserPayload BLANK_EMAIL_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email(" ")
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .build();

    public static final LoginUserPayload INVALID_EMAIL_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email("invalid")
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .build();

    // - Password
    public static final LoginUserPayload NULL_PASSWORD_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email(VALID_LOGIN_USER_REQUEST.getEmail())
            .password(null)
            .build();

    public static final LoginUserPayload BLANK_PASSWORD_LOGIN_USER_REQUEST = LoginUserRequest.builder()
            .email(VALID_LOGIN_USER_REQUEST.getEmail())
            .password(" ")
            .build();
}
