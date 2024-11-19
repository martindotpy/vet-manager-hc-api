package com.vet.hc.api.auth.adapter.out.data;

import com.vet.hc.api.auth.adapter.out.JwtAuthenticationServiceTest;
import com.vet.hc.api.user.domain.dto.UserDto;

/**
 * Data provider for {@link JwtAuthenticationServiceTest}.
 */
public final class JwtAuthenticationDataProvider {
    public static final UserDto USER_DTO = UserDto.builder()
            .id(1L)
            .email("john.doe@example.com")
            .build();
}
