package com.vet.hc.api.auth.application.service;

import com.vet.hc.api.auth.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.adapter.out.mapper.UserMapper;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.enums.UserRole;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RegisterUserService implements RegisterUserPort {
    private UserRepository userRepository;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public RegisterUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserDto, AuthFailure> register(RegisterUserCommand command) {
        User user = User.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .password(command.getPassword())
                .role(UserRole.USER)
                .build();
        System.out.println(user);
        user = userRepository.save(user);

        return Result.success(userMapper.toDto(user));
    }
}
