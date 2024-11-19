package com.vet.hc.api.auth.application.usecase;

import java.util.Optional;

import com.vet.hc.api.auth.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.auth.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.mapper.UserMapper;
import com.vet.hc.api.user.domain.dto.UserDto;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for logging in a user.
 */
@Slf4j
@NoArgsConstructor
public class LoginUserUseCase implements LoginUserPort {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public LoginUserUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserDto, AuthFailure> login(LoginUserPayload payload) {
        log.info("Logging in user with email: {}", payload.getEmail());

        Optional<User> userFound = userRepository.findByEmail(payload.getEmail());

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

        return Result.success(userMapper.toDto(userFound.get()));
    }
}
