package com.vet.hc.api.auth.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.adapter.in.util.DatabaseShortcuts.rollbackFailure;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.RegisterUserPayload;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Register user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Result<UserDto, AuthFailure> register(RegisterUserPayload payload) {
        log.info("Registering user with email: {}",
                fgBrightBlue(payload.getEmail()));

        // Verify if the email already exists with the same auth provider
        var findUserResult = userRepository.findByEmailDeletedOrNot(payload.getEmail());

        Result<User, UserFailure> saveUserResult = null;

        if (findUserResult.isEmpty()) {
            // Create the new user
            saveUserResult = create(payload);
        } else {
            User userFound = findUserResult.get();

            if (!userFound.isDeleted()) {
                log.error("User with email {} already exists",
                        fgBrightRed(payload.getEmail()));

                return failure(AuthFailure.EMAIL_ALREADY_IN_USE);
            }

            // Restore the user
            saveUserResult = restore(payload, userFound.getId());
        }

        if (saveUserResult.isFailure()) {
            UserFailure failure = saveUserResult.getFailure();

            log.error("Fail {} when register user with email: {}",
                    fgBrightRed(failure),
                    fgBrightRed(payload.getEmail()));

            return rollbackFailure(switch (failure) {
                case EMAIL_ALREADY_IN_USE -> AuthFailure.EMAIL_ALREADY_IN_USE;
                default -> AuthFailure.UNEXPECTED;
            }, saveUserResult.getValidationErrors().orElse(null));
        }

        // Generate JWT
        log.info("User with email {} registered successfully",
                fgBrightBlue(payload.getEmail()));

        return ok(userMapper.toDto(saveUserResult.getOk()));
    }

    private Result<User, UserFailure> create(RegisterUserPayload payload) {
        User newUser = userMapper.fromRegister(payload)
                .password(passwordEncoder.encode(payload.getPassword()))
                .roles(List.of(UserRole.USER))
                .build();

        return userRepository.save(newUser);
    }

    private Result<User, UserFailure> restore(RegisterUserPayload payload, Long id) {
        var restoreUserResult = userRepository.restoreUserByEmail(payload.getEmail());

        if (restoreUserResult.isFailure()) {
            return failure(restoreUserResult.getFailure());
        }

        return userRepository.update(id,
                FieldUpdate.set("first_name", payload.getFirstName()),
                FieldUpdate.set("lastName", payload.getLastName()),
                FieldUpdate.set("password", passwordEncoder.encode(payload.getPassword())));
    }
}
