package com.vet.hc.api.shared.adapter.in.util;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.domain.result.Result.failure;

import java.util.List;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for database shortcuts.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DatabaseShortcuts {
    /**
     * Rollback the transaction and return a failure.
     *
     * @param <S>     the success type.
     * @param <F>     the failure type.
     * @param failure the failure to return.
     * @return the failure.
     */
    public static <S, F extends Failure> Result<S, F> rollbackFailure(F failure) {
        log.debug("Rolling back transaction");

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        log.info("Transaction rolled back, returning failure '{}'",
                fgBrightRed(failure));

        return failure(failure);
    }

    public static <S, F extends Failure> Result<S, F> rollbackFailure(F failure,
            List<ValidationError> validationErrors) {
        log.debug("Rolling back transaction");

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        log.info("Transaction rolled back, returning failure '{}'",
                fgBrightRed(failure));

        return failure(failure, validationErrors);
    }
}
