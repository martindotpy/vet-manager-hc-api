package com.vet.hc.api.user.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.UpdateUserEmailPort;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.payload.UpdateUserEmailPayload;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

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
    public UserDto update(UpdateUserEmailPayload payload) {
        MDC.put("operationId", "User id " + payload.getId());
        log.info("Updating user email");

        var result = updateHelper(payload);

        return userMapper.toDto(result);
    }

    @Override
    @Transactional
    public JwtDto updateCurrentUser(UpdateUserEmailPayload payload) {
        MDC.put("operationId", "User id " + payload.getId());
        log.info("Updating current user email");

        var result = updateHelper(payload);
        String jwt = jwtAuthenticationPort.toJwt(result);

        return new JwtDto(jwt);
    }

    private User updateHelper(UpdateUserEmailPayload payload) {
        var userToUpdate = userRepository.findById(payload.getId());

        var user = userToUpdate.get();
        var userUpdated = userMapper.toBuilder(user)
                .email(payload.getNewEmail())
                .build();

        var result = userRepository.save(userUpdated);

        return result;
    }
}
