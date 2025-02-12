package com.vluepixel.vetmanager.api.auth.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vluepixel.vetmanager.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vluepixel.vetmanager.api.auth.core.application.dto.JwtDto;
import com.vluepixel.vetmanager.api.auth.core.application.port.in.LoginUserPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.InvalidCredentialsException;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.UserAlreadyAuthenticatedException;
import com.vluepixel.vetmanager.api.auth.core.domain.payload.LoginUserPayload;
import com.vluepixel.vetmanager.api.shared.application.annotations.UseCase;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Login user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class LoginUserUseCase implements LoginUserPort {
    private final GetCurrentUserPort getCurrentUserPort;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtAuthenticationPort jwtAuthenticationPort;

    @Override
    public JwtDto login(LoginUserPayload payload) {
        MDC.put("operationId", "User email " + payload.getEmail());
        log.info("Logging in user with email: {}",
                fgBrightBlue(payload.getEmail()));

        try {
            getCurrentUserPort.get();

            log.error("User already authenticated");
            throw new UserAlreadyAuthenticatedException();
        } catch (GetUserWhenDoNotLoggedInException ignored) {
        }

        // Find user by email
        User user = userRepository.findByEmail(payload.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException());

        // Check if password is correct
        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            log.error("Invalid password for user with email {}",
                    fgBrightRed(payload.getEmail()));

            throw new InvalidCredentialsException();
        }

        // Generate JWT
        log.info("User with email {} logged in successfully",
                fgBrightGreen(payload.getEmail()));

        String jwt = jwtAuthenticationPort.toJwt(user);

        return new JwtDto(jwt);
    }
}
