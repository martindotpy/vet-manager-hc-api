package com.vluepixel.vetmanager.api.auth.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.auth.core.application.port.in.RegisterUserPort;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.EmailAlreadyInUseException;
import com.vluepixel.vetmanager.api.auth.core.domain.payload.RegisterUserPayload;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto register(RegisterUserPayload payload) {
        log.info("Registering user with email: {}",
                fgBrightBlue(payload.getEmail()));

        // Verify if the email already exists with the same auth provider
        var findUser = userRepository.findByEmailDeletedOrNot(payload.getEmail());

        User savedUser = null;

        if (findUser.isEmpty()) {
            // Create the new user
            savedUser = create(payload);
        } else {
            User userFound = findUser.get();

            if (!userFound.isDeleted()) {
                log.error("User with email {} already exists",
                        fgBrightRed(payload.getEmail()));

                throw new EmailAlreadyInUseException();
            }

            // Restore the user
            savedUser = restore(payload, userFound.getId());
        }

        // Generate JWT
        log.info("User with email {} registered successfully",
                fgBrightBlue(payload.getEmail()));

        return userMapper.toDto(savedUser);
    }

    private User create(RegisterUserPayload payload) {
        User newUser = userMapper.fromRegister(payload)
                .password(passwordEncoder.encode(payload.getPassword()))
                .roles(List.of(UserRole.USER))
                .build();

        return userRepository.save(newUser);
    }

    private User restore(RegisterUserPayload payload, Long id) {
        userRepository.restoreUserByEmail(payload.getEmail());

        return userRepository.update(id,
                FieldUpdate.set("first_name", payload.getFirstName()),
                FieldUpdate.set("lastName", payload.getLastName()),
                FieldUpdate.set("password", passwordEncoder.encode(payload.getPassword())));
    }
}
