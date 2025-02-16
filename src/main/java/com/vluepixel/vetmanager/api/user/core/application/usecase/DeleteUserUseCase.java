package com.vluepixel.vetmanager.api.user.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.user.core.application.port.in.DeleteUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.exception.UserCannotDeleteItselfException;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete user use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteUserUseCase implements DeleteUserPort {
    private final GetCurrentUserPort getCurrentUserPort;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteById(Long id) {
        MDC.put("operationId", "User id " + id);
        log.info("Deleting user");

        if (id.equals(getCurrentUserPort.get().getId())) {
            log.warn("User with id: {} tried to delete itself",
                    fgBrightRed(id));

            throw new UserCannotDeleteItselfException();
        }

        userRepository.deleteById(id);

        log.info("User deleted");
    }
}
