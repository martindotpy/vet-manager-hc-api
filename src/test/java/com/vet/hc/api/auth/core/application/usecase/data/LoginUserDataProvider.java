package com.vet.hc.api.auth.core.application.usecase.data;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserDto;
import com.vet.hc.api.auth.core.application.usecase.LoginUserUseCaseTest;

/**
 * Data provider for {@link LoginUserUseCaseTest}.
 */
public final class LoginUserDataProvider {
    public static final LoginUserDto LOGIN_USER_DTO_CORRECT_PASSWORD = new LoginUserDto("john.doe@example.com",
            "password123");
    public static final LoginUserDto LOGIN_USER_DTO_WRONG_PASSWORD = new LoginUserDto("john.doe@example.com",
            "wrongPassword");
}
