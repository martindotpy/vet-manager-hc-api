package com.vet.hc.api.auth.core.application.usecase.data;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserRequest;

/**
 * // * Data provider for {@link LoginUserUseCaseTest}.
 */
public final class LoginUserDataProvider {
    public static final LoginUserRequest LOGIN_USER_REQUEST_CORRECT_PASSWORD = new LoginUserRequest(
            "john.doe@example.com",
            "password123");
    public static final LoginUserRequest LOGIN_USER_REQUEST_WRONG_PASSWORD = new LoginUserRequest(
            "john.doe@example.com",
            "wrongPassword");
}
