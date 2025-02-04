package com.vet.hc.api.user.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import org.slf4j.MDC;

import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.application.port.in.DeleteUserPort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

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
    public Result<Void, UserFailure> deleteById(Long id) {
        MDC.put("operationId", "User id " + id);
        log.info("Deleting user");

        if (id.equals(getCurrentUserPort.get().getId())) {
            log.warn("User with id: {} tried to delete itself",
                    fgBrightRed(id));

            return failure(UserFailure.USER_CANNOT_DELETE_THEMSELF);
        }

        var result = userRepository.deleteById(id);

        if (result.isFailure()) {
            return failure(result);
        }

        log.info("User with id '{}' deleted",
                fgBrightGreen(id));

        return ok();
    }
}
