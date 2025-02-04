package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.DatabaseShortcuts.rollbackFailure;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.List;

import org.jboss.logging.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.shared.domain.validation.ValidationError;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.application.port.in.UpdateUserPort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.payload.UpdateUserPayload;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUserPort {
    private final GetCurrentUserPort getCurrentUserPort;

    private final JwtAuthenticationPort jwtAuthenticationPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Result<UserDto, UserFailure> update(UpdateUserPayload payload) {
        var result = updateHelper(payload);

        if (result.isFailure()) {
            return failure(result);
        }

        return ok(userMapper.toDto(result.getOk()));
    }

    @Override
    @Transactional
    public Result<JwtDto, UserFailure> updateCurrentUser(UpdateUserPayload payload) {
        var result = updateHelper(payload);

        if (result.isFailure()) {
            return failure(result);
        }

        String jwt = jwtAuthenticationPort.toJwt(result.getOk());

        return ok(JwtDto.builder().jwt(jwt).build());
    }

    /**
     * Updates the current user.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the result
     */
    private Result<? extends User, UserFailure> updateHelper(UpdateUserPayload payload) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating current user");

        var result = userRepository.update(user.getId(),
                FieldUpdate.set("firstName", payload.getFirstName().trim()),
                FieldUpdate.set("lastName", payload.getLastName().trim()));

        if (result.isFailure()) {
            var failure = result.getFailure();
            List<ValidationError> validationErrors = switch (failure) {
                case FIRST_NAME_REQUIRED ->
                    List.of(new ValidationError("body.first_name", "El primer nombre es requerido"));
                case LAST_NAME_REQUIRED ->
                    List.of(new ValidationError("body.last_name", "El apellido es requerido"));
                default -> null;
            };

            return rollbackFailure(failure, validationErrors);
        }

        return result;
    }
}
