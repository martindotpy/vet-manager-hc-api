package com.vet.hc.api.auth.core.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for registering a new user.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class RegisterUserUseCase implements RegisterUserPort {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtAuthenticationPort jwtAuthenticationPort;

    @Override
    public Result<JwtDto, AuthFailure> register(RegisterUserPayload payload) {
        log.info("Registering user with email: {}", payload.getEmail());

        User user = User.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .password(passwordEncoder.encode(payload.getPassword()))
                .roles(payload.getRoles())
                .build();
        var userResult = userRepository.save(user);

        if (userResult.isFailure()) {
            log.info("Failed to register user with email: {}", payload.getEmail());

            RepositoryFailure failure = userResult.getFailure();

            if (failure == RepositoryFailure.DUPLICATED) {
                return Result.failure(AuthFailure.EMAIL_ALREADY_IN_USE);
            }

            throw new RuntimeException("Unexpected repository failure: " + failure);
        }

        String jwt = jwtAuthenticationPort.toJwt(userResult.getSuccess());

        return Result.success(JwtDto.builder().jwt(jwt).build());
    }
}
