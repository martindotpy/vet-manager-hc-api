package com.vet.hc.api.auth.core.application.usecase;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for logging in a user.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class LoginUserUseCase implements LoginUserPort {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtAuthenticationPort jwtAuthenticationPort;

    @Override
    public Result<JwtDto, AuthFailure> login(LoginUserPayload payload) {
        log.info("Logging in user with email: {}", payload.getEmail());

        Optional<? extends User> userFound = userRepository.findByEmail(payload.getEmail());

        if (userFound.isEmpty()) {
            log.info("User with email {} not found", payload.getEmail());

            return Result.failure(AuthFailure.INVALID_CREDENTIALS);
        }

        User user = userFound.get();
        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            log.info("Invalid password for user with email {}", payload.getEmail());

            return Result.failure(AuthFailure.INVALID_CREDENTIALS);
        }

        log.info("User with email {} logged in successfully", payload.getEmail());

        String jwt = jwtAuthenticationPort.toJwt(user);

        return Result.success(JwtDto.builder().jwt(jwt).build());
    }
}
