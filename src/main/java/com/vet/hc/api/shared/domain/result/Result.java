package com.vet.hc.api.shared.domain.result;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The result of a operation.
 *
 * <p>
 * It can be only a successful result or a failure.
 * </p>
 */
@ToString
@EqualsAndHashCode
public final class Result<S, F extends Failure> {
    private final S ok;
    private final F failure;
    private List<ValidationError> validationErrors;

    private Result(S success, F failure) {
        this.ok = success;
        this.failure = failure;
    }

    /**
     * Create a successful result.
     *
     * @param <S>     the type of the successful response.
     * @param <F>     the type of the failure response.
     * @param success the successful response.
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> ok(S success) {
        Objects.requireNonNull(success, "The success response must not be null");

        return new Result<>(success, null);
    }

    /**
     * Create an empty successful result.
     *
     * @param <S> the type of the successful response.
     * @param <E> the type of the failure response.
     * @return the result
     */
    public static <S, E extends Failure> Result<S, E> ok() {
        return new Result<>(null, null);
    }

    /**
     * Create a failure result.
     *
     * @param <S>     the type of the successful response.
     * @param <F>     the type of the failure response.
     * @param failure the failure response.
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> failure(F failure) {
        Objects.requireNonNull(failure, "The failure response must not be null");

        return new Result<>(null, failure);
    }

    /**
     * Create a failure result with validation errors.
     *
     * @param <S>              the type of the successful response.
     * @param <F>              the type of the failure response.
     * @param failure          the failure response.
     * @param validationErrors the validation errors.
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> failure(F failure, List<ValidationError> validationErrors) {
        Result<S, F> result = new Result<>(null, failure);

        if (validationErrors != null)
            result.validationErrors = new CopyOnWriteArrayList<>(validationErrors);

        return result;
    }

    /**
     * Create a failure result with validation errors.
     *
     * @param <S>              the type of the successful response.
     * @param <F>              the type of the failure response.
     * @param failure          the failure response.
     * @param validationErrors the validation errors.
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> failure(F failure, ValidationError... validationErrors) {
        return failure(failure, new CopyOnWriteArrayList<ValidationError>(validationErrors));
    }

    /**
     * Create a failure result from another result.
     *
     * @param <S>    the type of the successful response.
     * @param <F>    the type of the failure response.
     * @param result the result.
     * @return the result
     */
    public static <S, NS, F extends Failure> Result<NS, F> failure(Result<S, F> result) {
        Objects.requireNonNull(result, "The result must not be null");
        if (result.isOk()) {
            throw new IllegalArgumentException("The result must be a failure");
        }

        Result<NS, F> newResult = new Result<>(null, result.getFailure());
        newResult.validationErrors = result.getValidationErrors().orElse(null);

        return newResult;
    }

    public S getOk() {
        return ok;
    }

    public F getFailure() {
        return failure;
    }

    public Optional<List<ValidationError>> getValidationErrors() {
        return Optional.ofNullable(validationErrors);
    }

    public boolean isOk() {
        return ok != null || failure == null;
    }

    public boolean isFailure() {
        return failure != null;
    }
}
