package com.vet.hc.api.auth.application.service;

import com.vet.hc.api.auth.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.adapter.out.mapper.UserMapper;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service responsible for registering a new user.
 */
@NoArgsConstructor
public class RegisterUserService implements RegisterUserPort {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public RegisterUserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserDto, AuthFailure> register(RegisterUserCommand command) {
        User user = User.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .roles(command.getRoles())
                .build();
        var userResult = userRepository.save(user);

        if (userResult.isFailure()) {
            RepositoryFailure failure = userResult.getFailure();

            if (failure == RepositoryFailure.DUPLICATE) {
                return Result.failure(AuthFailure.EMAIL_ALREADY_IN_USE);
            }

            throw new RuntimeException("Unexpected repository failure: " + failure);
        }

        return Result.success(userMapper.toDto(userResult.getSuccess()));
    }
}
