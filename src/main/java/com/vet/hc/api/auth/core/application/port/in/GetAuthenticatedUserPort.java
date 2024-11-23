package com.vet.hc.api.auth.core.application.port.in;

import java.util.Optional;

import com.vet.hc.api.user.core.domain.dto.UserDto;

/**
 * Get authenticated user port.
 */
public interface GetAuthenticatedUserPort {
    Optional<UserDto> get();
}
