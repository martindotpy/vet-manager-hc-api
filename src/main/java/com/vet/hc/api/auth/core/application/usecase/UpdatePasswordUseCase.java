package com.vet.hc.api.auth.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.in.UpdatePasswordPort;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.auth.core.domain.payload.UpdatePasswordPayload;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
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
    public void update(UpdatePasswordPayload payload) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating password");

        // Verify with login port
        loginUserPort.login(LoginUserRequest.builder()
                .email(user.getEmail())
                .password(payload.getPassword())
                .build());

        // Change the password
        String newPassword = passwordEncoder.encode(payload.getNewPassword());
        userRepository.update(
                user.getId(),
                FieldUpdate.set("password", newPassword));
    }
}
