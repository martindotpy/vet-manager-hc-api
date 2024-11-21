package com.vet.hc.api.auth.core.adapter.out.data;

import com.vet.hc.api.auth.core.adapter.out.JwtAuthenticationServiceTest;
import com.vet.hc.api.user.core.domain.dto.UserDto;

/**
 * Data provider for {@link JwtAuthenticationServiceTest}.
 */
public final class JwtAuthenticationDataProvider {
    public static final UserDto USER_DTO = UserDto.builder()
            .id(1L)
            .email("john.doe@example.com")
            .build();
}
