package com.vet.hc.api.auth.core.application.usecase.data;

import java.util.Set;

import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.user.core.domain.enums.UserRole;

/**
 * // * Data provider for {@link RegisterUserUseCaseTest}.
 */
public final class RegisterUserDataProvider {
    public static final RegisterUserRequest REGISTER_USER_REQUEST = new RegisterUserRequest(
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            "password123",
            Set.of(UserRole.USER));
}
