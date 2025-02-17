package com.vluepixel.vetmanager.api.shared.adapter.out.persistence.repository.mysql;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;
import static com.vluepixel.vetmanager.api.shared.domain.util.SpanishUtils.getName;

import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.hibernate.TransientObjectException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import com.vluepixel.vetmanager.api.shared.adapter.in.validation.JakartaValidator;
import com.vluepixel.vetmanager.api.shared.domain.exception.ConflictException;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.RepositoryException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.repository.RepositoryErrorType;
import com.vluepixel.vetmanager.api.shared.domain.repository.RepositoryExceptionHandler;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

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
public final class MySQLRepositoryExceptionHandler implements RepositoryExceptionHandler {
    @Override
    public void handle(RepositoryException e) {
        handle(e.getCause(), e.getEntityClass());
    }

    private void handle(Throwable e, Class<?> entityClass) {
        log.debug("Handling error '{}' with message: {}",
                fgBrightRed(e.getClass().getName()),
                fgBrightRed(e.getMessage()));

        if (e instanceof jakarta.persistence.RollbackException) {
            handle(e.getCause(), entityClass);
        }

        else if (e instanceof TransactionSystemException) {
            handle(e.getCause(), entityClass);
        }

        else if (e instanceof DataIntegrityViolationException) {
            handle(e.getCause(), entityClass);
        }

        else if (e instanceof jakarta.validation.ConstraintViolationException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof org.hibernate.exception.ConstraintViolationException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof InvalidDataAccessApiUsageException childException) {
            handle(childException.getMostSpecificCause(), entityClass);
        }

        else if (e instanceof TransientObjectException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof OptimisticLockingFailureException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof PathElementException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof NoResultException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof NonUniqueResultException childException) {
            handle(childException, entityClass);
        }

        else if (e instanceof StaleObjectStateException childException) {
            handle(childException, entityClass);
        }

        log.error("Unexpected MySQL error", e);
    }

    private void handle(jakarta.validation.ConstraintViolationException e, Class<?> entityClass) {
        var violations = e.getConstraintViolations();
        List<ValidationError> validationErrors = JakartaValidator.fromViolations(violations);

        throw new ValidationException(validationErrors);
    }

    private void handle(org.hibernate.exception.ConstraintViolationException e, Class<?> entityClass) {
        RepositoryErrorType type = MySQLErrorCodes.getTypeFromErrorCode(e.getErrorCode());

        if (type == RepositoryErrorType.DUPLICATED) {
            String constraint = e.getConstraintName();
            String[] splittedConstraint = constraint.split("\\.");
            String field = splittedConstraint[splittedConstraint.length - 1];

            // Remove the 'UK_' prefix, the table name and the "_" after the table name
            field = field.substring(4 + splittedConstraint[0].length());

            throw new ConflictException(entityClass, field);
        }

        else if (type == RepositoryErrorType.FOREIGN_KEY_CONSTRAINT_FAIL) {
            String field = e.getErrorMessage().split("CONSTRAINT `")[1].split("`")[0];

            throw new NotFoundException(field);
        }

        else if (type == RepositoryErrorType.NOT_NULL) {
            String field = e.getSQLException().getMessage().split("'")[1];

            throw new ValidationException(
                    List.of(new ValidationError(
                            toSnakeCase(field),
                            getName(entityClass, field) + " no puede ser nulo(a)")));
        }
    }

    private void handle(TransientObjectException e, Class<?> entityClass) {
        throw new NotFoundException(entityClass);
    }

    private void handle(OptimisticLockingFailureException e, Class<?> entityClass) {
        throw new NotFoundException(entityClass);
    }

    private void handle(PathElementException e, Class<?> entityClass) {
        // Get the entity and field from the exception message
        String[] splittedMessageByColon = e.getLocalizedMessage().split("'");
        String[] splittedMessageByDots = splittedMessageByColon[3].split("\\.");
        String entity = toSnakeCase(splittedMessageByDots[splittedMessageByDots.length - 1].split("Entity")[0]);
        String field = entity + "." + splittedMessageByColon[1];

        log.error("Cannot find field '{}' in entity '{}'",
                fgBrightRed(field),
                fgBrightRed(entity));

        throw new InternalServerErrorException(e);
    }

    private void handle(NoResultException e, Class<?> entityClass) {
        throw new NotFoundException(entityClass);
    }

    private void handle(NonUniqueResultException e, Class<?> entityClass) {
        throw new InternalServerErrorException(e);
    }

    private void handle(StaleObjectStateException e, Class<?> entityClass) {
        throw new InternalServerErrorException(e);
    }
}
