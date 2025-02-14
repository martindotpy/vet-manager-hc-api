package com.vluepixel.vetmanager.api.auth.core.data;

import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_USER_REQUEST;

import com.vluepixel.vetmanager.api.auth.core.domain.request.UpdatePasswordRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Update password user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePasswordUserDataProvider {
    private static final int MAX_PASSWORD_LENGTH = 60;
    // Role: USER
    public static final UpdatePasswordRequest VALID_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .newPassword("h".repeat(MAX_PASSWORD_LENGTH))
            .build();

    // - Invalid arguments
    // Password
    public static final UpdatePasswordRequest INVALID_PASSWORD_WRONG_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password("otherpassword")
            .newPassword(VALID_UPDATE_USER_PASSWORD_REQUEST.getNewPassword())
            .build();

    public static final UpdatePasswordRequest INVALID_PASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(" ")
            .newPassword(VALID_UPDATE_USER_PASSWORD_REQUEST.getNewPassword())
            .build();

    public static final UpdatePasswordRequest INVALID_PASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password("")
            .newPassword(VALID_UPDATE_USER_PASSWORD_REQUEST.getNewPassword())
            .build();

    public static final UpdatePasswordRequest INVALID_PASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(null)
            .newPassword(VALID_UPDATE_USER_PASSWORD_REQUEST.getNewPassword())
            .build();

    // NewPassword
    public static final UpdatePasswordRequest INVALID_NEWPASSWORD_TOOLONG_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .newPassword("h".repeat(MAX_PASSWORD_LENGTH + 1))
            .build();

    public static final UpdatePasswordRequest INVALID_NEWPASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .newPassword(" ")
            .build();

    public static final UpdatePasswordRequest INVALID_NEWPASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .newPassword("")
            .build();

    public static final UpdatePasswordRequest INVALID_NEWPASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_USER_REQUEST.getPassword())
            .newPassword(null)
            .build();

    // Role: ADMIN
    public static final UpdatePasswordRequest VALID_UPDATE_ADMIN_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password(VALID_LOGIN_ADMIN_REQUEST.getPassword())
            .newPassword("h".repeat(MAX_PASSWORD_LENGTH))
            .build();
}
