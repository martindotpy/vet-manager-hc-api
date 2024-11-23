package com.vet.hc.api.auth.core.application.usecase;

import java.util.Optional;

import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Get authenticated user use case.
 */
@Slf4j
@RequestScoped
@NoArgsConstructor
public final class GetAuthenticatedUserUseCase implements GetAuthenticatedUserPort {
    @Inject
    private HttpServletRequest request;

    @Override
    public Optional<UserDto> get() {
        Optional<UserDto> user = Optional.ofNullable((UserDto) request.getAttribute("user"));

        log.info(user.isPresent() ? "User found: " + user.get().getId() : "User not found");

        return user;
    }
}
