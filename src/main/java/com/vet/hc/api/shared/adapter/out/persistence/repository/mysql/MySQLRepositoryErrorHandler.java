package com.vet.hc.api.shared.adapter.out.persistence.repository.mysql;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.domain.util.CaseConverterUtil.toSnakeCase;

import java.util.List;

import org.hibernate.TransientObjectException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.shared.adapter.in.validation.JakartaValidator;
import com.vet.hc.api.shared.domain.repository.RepositoryError;
import com.vet.hc.api.shared.domain.repository.RepositoryError.RepositoryErrorBuilder;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorHandler;
import com.vet.hc.api.shared.domain.repository.RepositoryErrorType;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Error handler for MySQL database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class MySQLRepositoryErrorHandler implements RepositoryErrorHandler {
    private final ObjectMapper objectMapper;

    @Override
    public RepositoryError handle(Throwable e) {
        log.debug("Handling error '{}' with message: {}",
                fgBrightRed(e.getClass().getName()),
                fgBrightRed(e.getMessage()));

        if (e instanceof jakarta.persistence.RollbackException) {
            return handle(e.getCause());
        }

        else if (e instanceof TransactionSystemException) {
            return handle(e.getCause());
        }

        else if (e instanceof DataIntegrityViolationException) {
            return handle(e.getCause());
        }

        else if (e instanceof jakarta.validation.ConstraintViolationException childException) {
            return handle(childException);
        }

        else if (e instanceof org.hibernate.exception.ConstraintViolationException childException) {
            return handle(childException);
        }

        else if (e instanceof InvalidDataAccessApiUsageException childException) {
            return handle(childException.getMostSpecificCause());
        }

        else if (e instanceof TransientObjectException childException) {
            return handle(childException);
        }

        else if (e instanceof OptimisticLockingFailureException childException) {
            return handle(childException);
        }

        else if (e instanceof PathElementException childException) {
            return handle(childException);
        }

        else if (e instanceof NoResultException childException) {
            return handle(childException);
        }

        else if (e instanceof NonUniqueResultException childException) {
            return handle(childException);
        }

        log.error("Unexpected MySQL error", e);

        throw new RuntimeException(e);
    }

    private RepositoryError handle(jakarta.validation.ConstraintViolationException e) {
        RepositoryErrorType type = RepositoryErrorType.VALIDATION_FAIL;
        var violations = e.getConstraintViolations();
        List<ValidationError> validationErrors = JakartaValidator.fromViolations(violations);
        RepositoryErrorBuilder repositoryError;

        log.warn("Unhandled validations: {}",
                fgBrightRed(validationErrors));

        try {
            repositoryError = RepositoryError.builder()
                    .field(objectMapper.writeValueAsString(validationErrors))
                    .type(type);
        } catch (JsonProcessingException e1) {
            throw new RuntimeException(e1);
        }

        return repositoryError.build();
    }

    private RepositoryError handle(org.hibernate.exception.ConstraintViolationException e) {
        RepositoryErrorType type = MySQLErrorCodes.getTypeFromErrorCode(e.getErrorCode());
        var repositoryError = RepositoryError.builder()
                .type(type);

        if (type == RepositoryErrorType.DUPLICATED) {
            String field = e.getConstraintName();

            repositoryError.field(field);
        }

        else if (type == RepositoryErrorType.FOREIGN_KEY_CONSTRAINT_FAIL) {
            String field = e.getErrorMessage().split("CONSTRAINT `")[1].split("`")[0];

            repositoryError.field(field);
        }

        else if (type == RepositoryErrorType.NOT_NULL) {
            String field = e.getSQLException().getMessage().split("'")[1];

            repositoryError.field(field);
        }

        return repositoryError.build();
    }

    private RepositoryError handle(TransientObjectException e) {
        RepositoryErrorType type = RepositoryErrorType.NOT_FOUND;
        var repositoryError = RepositoryError.builder()
                .type(type);

        return repositoryError.build();
    }

    private RepositoryError handle(OptimisticLockingFailureException e) {
        RepositoryErrorType type = RepositoryErrorType.OPTIMISTIC_LOCK_FAIL;
        var repositoryError = RepositoryError.builder()
                .type(type);

        return repositoryError.build();
    }

    private RepositoryError handle(PathElementException e) {
        RepositoryErrorType type = RepositoryErrorType.FIELD_NOT_FOUND;
        var repositoryError = RepositoryError.builder()
                .type(type);

        // Get the entity and field from the exception message
        String[] splittedMessageByColon = e.getLocalizedMessage().split("'");
        String[] splittedMessageByDots = splittedMessageByColon[3].split("\\.");
        String entity = toSnakeCase(splittedMessageByDots[splittedMessageByDots.length - 1].split("Entity")[0]);
        String field = entity +
                "." +
                splittedMessageByColon[1];

        repositoryError.field(field);

        return repositoryError.build();
    }

    private RepositoryError handle(NoResultException e) {
        RepositoryErrorType type = RepositoryErrorType.NOT_FOUND;
        var repositoryError = RepositoryError.builder()
                .type(type);

        return repositoryError.build();
    }

    private RepositoryError handle(NonUniqueResultException e) {
        RepositoryErrorType type = RepositoryErrorType.NON_UNIQUE_RESULT;
        var repositoryError = RepositoryError.builder()
                .type(type);

        return repositoryError.build();
    }
}
