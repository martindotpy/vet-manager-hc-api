package com.vet.hc.api.auth.application.service;

import java.util.Optional;

import com.vet.hc.api.auth.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.domain.command.LoginUserCommand;
import com.vet.hc.api.auth.domain.failure.InvalidCredentials;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.adapter.out.mapper.UserMapper;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for logging in a user.
 */
@NoArgsConstructor
public class LoginUserService implements LoginUserPort {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public LoginUserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserDto, InvalidCredentials> login(LoginUserCommand command) {
        Optional<User> userFound = userRepository.findByEmail(command.getEmail());

        if (userFound.isEmpty()) {
            return Result.failure(new InvalidCredentials());
        }

        User user = userFound.get();
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            return Result.failure(new InvalidCredentials());
        }

        return Result.success(userMapper.toDto(userFound.get()));
    }
}
