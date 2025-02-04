package com.vet.hc.api.auth.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vet.hc.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vet.hc.api.auth.core.application.dto.JwtDto;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.auth.core.domain.payload.LoginUserPayload;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

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
    public Result<JwtDto, AuthFailure> login(LoginUserPayload payload) {
        MDC.put("operationId", "User email " + payload.getEmail());
        log.info("Logging in user with email: {}",
                fgBrightBlue(payload.getEmail()));

        try {
            getCurrentUserPort.get();

            log.error("User already authenticated");
            return failure(AuthFailure.ALREADY_AUTHENTICATED);
        } catch (GetUserWhenDoNotLoggedInException ignored) {
        }

        // Find user by email
        Optional<? extends User> user = userRepository.findByEmail(payload.getEmail());

        if (user.isEmpty()) {
            log.error("User with email {} not found",
                    fgBrightRed(payload.getEmail()));

            return failure(AuthFailure.INVALID_CREDENTIALS);
        }

        // Check if password is correct
        User userFound = user.get();
        if (!passwordEncoder.matches(payload.getPassword(), userFound.getPassword())) {
            log.error("Invalid password for user with email {}",
                    fgBrightRed(payload.getEmail()));

            return failure(AuthFailure.INVALID_CREDENTIALS);
        }

        // Generate JWT
        log.info("User with email {} logged in successfully",
                fgBrightGreen(payload.getEmail()));

        String jwt = jwtAuthenticationPort.toJwt(userFound);

        return ok(JwtDto.builder().jwt(jwt).build());
    }
}
