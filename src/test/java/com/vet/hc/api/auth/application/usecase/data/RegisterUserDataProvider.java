package com.vet.hc.api.auth.application.usecase.data;

import java.util.Set;

import com.vet.hc.api.auth.adapter.in.request.RegisterUserDto;
import com.vet.hc.api.auth.application.usecase.RegisterUserUseCaseTest;
import com.vet.hc.api.user.domain.enums.UserRole;

/**
 * Data provider for {@link RegisterUserUseCaseTest}.
 */
public final class RegisterUserDataProvider {
    public static final RegisterUserDto REGISTER_USER_DTO = new RegisterUserDto(
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            "password123",
            Set.of(UserRole.USER));
}
