package com.vet.hc.api.shared.application.adapter.in.util;

import static com.vet.hc.api.shared.application.adapter.in.util.ResponseShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.application.adapter.in.util.ResponseShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.application.adapter.in.util.ResponseShortcuts.toOkResponse;
import static com.vet.hc.api.shared.application.adapter.in.util.ResponseShortcuts.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;

import com.vet.hc.api.shared.application.adapter.in.response.ContentResponse;
import com.vet.hc.api.shared.application.adapter.in.response.PaginatedResponse;
import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.shared.domain.validation.Validation;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Crud controller shortcuts.
 *
 * <p>
 * This class provides methods to simplify the implementation of the CRUD.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerShortcuts {
    /**
     * Respond paginated content.
     *
     * <p>
     * The paginated criteria supplier is invoked only if the validations pass to
     * prevent exceptions.
     * </p>
     *
     * @param <T>                     the type of the body.
     * @param contentResponseClass    the content response class.
     * @param paginatedResultSupplier the paginated result supplier.
     * @param message                 the message.
     * @return the response entity
     */
    public static <T> ResponseEntity<?> respondPaginatedContent(
            Class<? extends PaginatedResponse<T>> contentResponseClass,
            Supplier<Result<Paginated<T>, ? extends Failure>> paginatedResultSupplier,
            String message,
            Validation... validations) {
        var violations = new CopyOnWriteArrayList<ValidationError>();

        violations.addAll(validate(validations));

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        var result = paginatedResultSupplier.get();

        if (result.isFailure()) {
            return toFailureResponse(result);
        }

        return toPaginatedResponse(contentResponseClass, result.getOk(), message);
    }

    /**
     * Respond content result.
     *
     * @param <T>                  the type of the body.
     * @param <F>                  the type of the failure.
     * @param contentResponseClass the class of the content response.
     * @param supplier             the supplier.
     * @param message              the message.
     * @param validations          the validations.
     * @return the response entity
     */
    public static <T, F extends Failure> ResponseEntity<?> respondContentResult(
            Class<? extends ContentResponse<T>> contentResponseClass,
            Supplier<Result<T, F>> supplier,
            String message,
            Validation... validations) {
        return respondContentResult(contentResponseClass, supplier, t -> message, validations);
    }

    public static <T, F extends Failure> ResponseEntity<?> respondContentResult(
            Class<? extends ContentResponse<T>> contentResponseClass,
            Supplier<Result<T, F>> supplier,
            Function<T, String> messageSupplier,
            Validation... validations) {
        var violations = new CopyOnWriteArrayList<ValidationError>();

        violations.addAll(validate(validations));

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        var result = supplier.get();

        if (result.isFailure()) {
            return toFailureResponse(result);
        }

        return toOkResponse(contentResponseClass, result.getOk(), messageSupplier.apply(result.getOk()));
    }

    /**
     * Void result.
     *
     * @param <F>         the type of the failure.
     * @param supplier    the supplier.
     * @param message     the message.
     * @param validations the validations.
     * @return the response entity
     */
    public static <F extends Failure> ResponseEntity<?> respondVoidResult(
            Supplier<Result<Void, F>> supplier,
            String message,
            Validation... validations) {
        var violations = new CopyOnWriteArrayList<ValidationError>();

        violations.addAll(validate(validations));

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        var result = supplier.get();

        if (result.isFailure()) {
            return toFailureResponse(result);
        }

        return toOkResponse(message);
    }

    /**
     * Respond content.
     *
     * @param <T>                  the type of the body.
     * @param contentResponseClass the content response class.
     * @param supplier             the supplier.
     * @param message              the message.
     * @param validations          the validations.
     * @return the response entity
     */
    public static <T> ResponseEntity<?> respondContent(
            Class<? extends ContentResponse<T>> contentResponseClass,
            Supplier<T> supplier,
            String message,
            Validation... validations) {
        var violations = new CopyOnWriteArrayList<ValidationError>();

        violations.addAll(validate(validations));

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        return toOkResponse(contentResponseClass, supplier.get(), message);
    }
}
