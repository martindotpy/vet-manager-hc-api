package com.vet.hc.api.auth.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for registering a new user.
 */
@Slf4j
@NoArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public RegisterUserUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserDto, AuthFailure> register(RegisterUserPayload payload) {
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

            if (failure == RepositoryFailure.DUPLICATE) {
                return Result.failure(AuthFailure.EMAIL_ALREADY_IN_USE);
            }

            throw new RuntimeException("Unexpected repository failure: " + failure);
        }

        return Result.success(userMapper.toDto(userResult.getSuccess()));
    }
}
