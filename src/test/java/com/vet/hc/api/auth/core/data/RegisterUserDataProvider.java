package com.vet.hc.api.auth.core.data;

import java.util.List;

import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.user.core.adapter.in.response.UserResponse;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Register user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserDataProvider {
    public static final RegisterUserPayload VALID_REGISTER_USER_REQUEST = RegisterUserRequest.builder()
            .firstName("New")
            .lastName("User")
            .email("new-user@new-user.com")
            .password("password")
            .build();
    public static final UserResponse VALID_REGISTER_USER_RESPONSE = UserResponse.builder()
            .message("Usuario " + VALID_REGISTER_USER_REQUEST.getEmail() + " ha sido registrado correctamente")
            .content(UserDto.builder()
                    .id(3L)
                    .firstName(VALID_REGISTER_USER_REQUEST.getFirstName())
                    .lastName(VALID_REGISTER_USER_REQUEST.getLastName())
                    .email(VALID_REGISTER_USER_REQUEST.getEmail())
                    .roles(List.of(UserRole.USER))
                    .profileImage(null)
                    .build())
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------------------

    // - First name
}
