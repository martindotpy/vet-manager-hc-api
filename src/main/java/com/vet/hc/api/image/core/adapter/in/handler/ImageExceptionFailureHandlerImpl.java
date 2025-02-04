package com.vet.hc.api.image.core.adapter.in.handler;

import static com.vet.hc.api.shared.domain.result.Result.failure;

import org.springframework.stereotype.Component;

import com.vet.hc.api.image.core.domain.failure.ImageExceptionFailureHandler;
import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.domain.repository.RepositoryError;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorHandler;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorType;
import com.vet.hc.api.shared.domain.result.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for image exception failure handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class ImageExceptionFailureHandlerImpl implements ImageExceptionFailureHandler {
    private final RepositoryErrorHandler repositoryErrorHandler;

    @Override
    public <T> Result<T, ImageFailure> handle(Throwable e) {
        RepositoryError repositoryError = repositoryErrorHandler.handle(e);
        RepositoryErrorType type = repositoryError.getType();

        return failure(switch (type) {
            default -> {
                log.error("Unexpected image error", e);

                yield ImageFailure.UNEXPECTED;
            }
        });
    }
}
