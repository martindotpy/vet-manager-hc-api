package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.DatabaseShortcuts.rollbackFailure;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.UpdateUserEmailPort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
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
public final class UpdateUserEmailUseCase implements UpdateUserEmailPort {
    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Result<UserDto, UserFailure> update(UpdateUserEmailPayload payload) {
        MDC.put("operationId", "User id " + payload.getId());
        log.info("Updating user email");

        var result = updateHelper(payload);

        if (result.isFailure()) {
            return rollbackFailure(result.getFailure(), result.getValidationErrors().get());
        }

        return ok(userMapper.toDto(result.getOk()));
    }

    @Override
    @Transactional
    public Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserEmailPayload payload) {
        MDC.put("operationId", "User id " + payload.getId());
        log.info("Updating current user email");

        var result = updateHelper(payload);

        if (result.isFailure()) {
            return rollbackFailure(result.getFailure(), result.getValidationErrors().get());
        }

        var user = result.getOk();
        String jwt = jwtAuthenticationPort.toJwt(user);

        return ok(new JwtDto(jwt));
    }

    private Result<User, UserFailure> updateHelper(UpdateUserEmailPayload payload) {
        var userToUpdate = userRepository.findById(payload.getId());

        if (userToUpdate.isEmpty()) {
            return failure(UserFailure.NOT_FOUND);
        }

        var user = userToUpdate.get();
        var userUpdated = userMapper.toBuilder(user)
                .email(payload.getNewEmail())
                .build();

        var result = userRepository.save(userUpdated);

        if (result.isFailure()) {
            return failure(UserFailure.UNEXPECTED);
        }

        return ok(result.getOk());
    }
}
