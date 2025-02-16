package com.vluepixel.vetmanager.api.auth.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.util.List;
import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.port.in.RegisterUserPort;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.EmailAlreadyInUseException;
import com.vluepixel.vetmanager.api.auth.core.domain.request.RegisterUserRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.model.enums.UserRole;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Register user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto register(RegisterUserRequest request) {
        MDC.put("operationId", "User email " + request.getEmail());
        log.info("Registering user");

        // Verify if the email already exists with the same auth provider
        Optional<User> findUser = userRepository.findByEmailDeletedOrNot(request.getEmail());

        User savedUser = null;

        if (findUser.isEmpty()) { // Create the new user
            savedUser = create(request);
        } else {
            User userFound = findUser.get();

            if (!userFound.isDeleted()) {
                log.error("User with email already exists",
                        fgBrightRed(request.getEmail()));

                throw new EmailAlreadyInUseException();
            }

            // Restore the user
            savedUser = restore(request, userFound.getId());
        }

        log.info("User registered");

        return userMapper.toDto(savedUser);
    }

    private User create(RegisterUserRequest request) {
        User newUser = userMapper.fromRegister(request)
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(UserRole.USER))
                .build();

        return userRepository.save(newUser);
    }

    private User restore(RegisterUserRequest request, Long id) {
        userRepository.restoreUserByEmail(request.getEmail());

        return userRepository.update(id,
                FieldUpdate.set("first_name", request.getFirstName()),
                FieldUpdate.set("lastName", request.getLastName()),
                FieldUpdate.set("password", passwordEncoder.encode(request.getPassword())));
    }
}
