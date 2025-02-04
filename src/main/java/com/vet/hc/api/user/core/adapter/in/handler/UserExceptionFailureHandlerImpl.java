package com.vet.hc.api.user.core.adapter.in.handler;

import static com.vet.hc.api.shared.domain.result.Result.failure;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.domain.repository.RepositoryError;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorHandler;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorType;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.domain.failure.UserExceptionFailureHandler;
import com.vet.hc.api.user.core.domain.failure.UserFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for user exception failure handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class UserExceptionFailureHandlerImpl implements UserExceptionFailureHandler {
    private final RepositoryErrorHandler repositoryErrorHandler;

    @Override
    public <T> Result<T, UserFailure> handle(Throwable e) {
        RepositoryError repositoryError = repositoryErrorHandler.handle(e);
        RepositoryErrorType type = repositoryError.getType();
        String field = repositoryError.getField();

        return failure(switch (type) {
            case NOT_FOUND -> UserFailure.NOT_FOUND;
            case DUPLICATED -> {
                if ("UK_USER_EMAIL".equals(field)) {
                    yield UserFailure.EMAIL_ALREADY_IN_USE;
                }

                log.error("Unexpected field: {}", field);

                yield UserFailure.UNEXPECTED;
            }
            case NOT_NULL -> {
                if ("first_name".equals(field)) {
                    yield UserFailure.FIRST_NAME_REQUIRED;
                }

                else if ("last_name".equals(field)) {
                    yield UserFailure.LAST_NAME_REQUIRED;
                }

                log.error("Unexpected field: {}", field);

                yield UserFailure.UNEXPECTED;

            }
            default -> {
                log.error("Unexpected user error", e);

                yield UserFailure.UNEXPECTED;
            }
        });
    }
}
