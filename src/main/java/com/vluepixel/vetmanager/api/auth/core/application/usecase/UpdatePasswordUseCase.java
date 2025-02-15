package com.vluepixel.vetmanager.api.auth.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.port.in.UpdatePasswordPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.InvalidCredentialsException;
import com.vluepixel.vetmanager.api.auth.core.domain.request.UpdatePasswordRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

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

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void update(UpdatePasswordRequest request) {
        User user = getCurrentUserPort.get();
        MDC.put("operationId", "User id " + user.getId());
        log.info("Updating password");

        // Verify if the current password is correct
        User userFound = userRepository.findByEmail(user.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Change the password
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        userRepository.update(
                user.getId(),
                FieldUpdate.set("password", newPassword));

        log.info("Password updated");
    }
}
