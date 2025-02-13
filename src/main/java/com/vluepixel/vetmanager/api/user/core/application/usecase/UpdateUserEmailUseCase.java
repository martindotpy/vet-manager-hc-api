package com.vluepixel.vetmanager.api.user.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserEmailPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserEmailRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update user email use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateUserEmailUseCase implements UpdateUserEmailPort {
    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto update(UpdateUserEmailRequest request) {
        MDC.put("operationId", "User id " + request.getId());
        log.info("Updating user email");

        var result = updateHelper(request);

        return userMapper.toDto(result);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserEmailRequest request) {
        MDC.put("operationId", "User id " + request.getId());
        log.info("Updating current user email");

        var result = updateHelper(request);
        String jwt = jwtAuthenticationPort.toJwt(result);

        return new JwtDto(jwt);
    }

    private User updateHelper(UpdateUserEmailRequest request) {
        var userToUpdate = userRepository.findById(request.getId());

        var user = userToUpdate.get();
        var userUpdated = userMapper.toBuilder(user)
                .email(request.getNewEmail())
                .build();

        var result = userRepository.save(userUpdated);

        return result;
    }
}
