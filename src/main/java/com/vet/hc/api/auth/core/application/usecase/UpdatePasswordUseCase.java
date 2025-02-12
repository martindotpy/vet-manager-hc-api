package com.vet.hc.api.auth.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.DatabaseShortcuts.rollbackFailure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.in.UpdatePasswordPort;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.UpdatePasswordPayload;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update password use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdatePasswordUseCase implements UpdatePasswordPort {
    private final GetCurrentUserPort getCurrentUserPort;

    private final LoginUserPort loginUserPort;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Result<Void, AuthFailure> update(UpdatePasswordPayload payload) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating password");

        // Verify with login port
        var authenticationResult = loginUserPort.login(LoginUserRequest.builder()
                .email(user.getEmail())
                .password(payload.getPassword())
                .build());

        if (authenticationResult.isFailure()) {
            return rollbackFailure(authenticationResult.getFailure());
        }

        // Change the password
        String newPassword = passwordEncoder.encode(payload.getNewPassword());
        var result = userRepository.update(
                user.getId(),
                FieldUpdate.set("password", newPassword));

        if (result.isFailure()) {
            return rollbackFailure(AuthFailure.UNEXPECTED);
        }

        return ok();
    }
}
