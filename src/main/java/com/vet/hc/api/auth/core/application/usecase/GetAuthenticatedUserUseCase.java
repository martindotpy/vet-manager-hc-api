package com.vet.hc.api.auth.core.application.usecase;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Get authenticated user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class GetAuthenticatedUserUseCase implements GetAuthenticatedUserPort {
    @Override
    public Optional<UserDto> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.warn("No authentication found");
            return Optional.empty();
        }

        UserDto user = (UserDto) authentication.getPrincipal();

        if (user == null) {
            log.warn("No user found");
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
