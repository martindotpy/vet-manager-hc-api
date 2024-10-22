package com.vet.hc.api.shared.domain.query;

import java.util.Objects;

/**
 * The response of the query.
 *
 * <p>
 * It can be only a successful result or a failure.
 * </p>
 */
public final class Result<S, E extends Failure> {
    private final S success;
    private final E error;

    private Result(S success, E error) {
        this.success = success;
        this.error = error;
    }

    /**
     * Create a successful result.
     *
     * @param <S>     the type of the successful response.
     * @param <E>     the type of the failure response.
     * @param success the successful response.
     * @return the result
     */
    public static <S, E extends Failure> Result<S, E> success(S success) {
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
    public static <S, E extends Failure> Result<S, E> success() {
        return new Result<>(null, null);
    }

    /**
     * Create a failure result.
     *
     * @param <S>   the type of the successful response.
     * @param <E>   the type of the failure response.
     * @param error the failure response.
     * @return the result
     */
    public static <S, E extends Failure> Result<S, E> failure(E error) {
        Objects.requireNonNull(error, "The error response must not be null");
        return new Result<>(null, error);
    }

    public S getSuccess() {
        return success;
    }

    public E getError() {
        return error;
    }

    public boolean isSuccess() {
        return success != null || error == null;
    }

    public boolean isFailure() {
        return error != null;
    }
}
