package com.vluepixel.vetmanager.api.user.core.application.usecase;

import org.jboss.logging.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUserPort {
    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto update(UpdateUserRequest request) {
        MDC.put("operationId", "User id " + request.getId());
        log.info("Updating user info");

        User userUpdated = updateHelper(request);

        log.info("User updated");

        return userMapper.toDto(userUpdated);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserRequest request) {
        MDC.put("operationId", "User id " + request.getId());
        log.info("Updating current user info");

        User userUpdated = updateHelper(request);
        String jwt = jwtAuthenticationPort.toJwt(userUpdated);

        log.info("Current user updated");

        return new JwtDto(jwt);
    }

    private User updateHelper(UpdateUserRequest request) {
        User userUpdated = userRepository.update(request.getId(),
                FieldUpdate.set("firstName", request.getFirstName().trim()),
                FieldUpdate.set("lastName", request.getLastName().trim()));

        return userUpdated;
    }
}
