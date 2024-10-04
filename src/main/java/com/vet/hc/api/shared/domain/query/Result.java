package com.vet.hc.api.shared.domain.query;

/**
 * The response of the query.
 *
 * <p>
 * It can be only a successful result or a failure.
 * </p>
 */
public final class Result<S extends SuccessfulResponse, E extends FailureResponse> {
    private final S success;
    private final E error;

    private Result(S success, E error) {
        this.success = success;
        this.error = error;
    }

    /**
     * Create a successful result.
     *
     * @param <S>     the type of the successful response
     * @param <E>     the type of the failure response
     * @param success the successful response
     * @return the result
     */
    public static <S extends SuccessfulResponse, E extends FailureResponse> Result<S, E> success(S success) {
        return new Result<>(success, null);
    }

    /**
     * Create a failure result.
     *
     * @param <S>   the type of the successful response
     * @param <E>   the type of the failure response
     * @param error the failure response
     * @return the result
     */
    public static <S extends SuccessfulResponse, E extends FailureResponse> Result<S, E> failure(E error) {
        return new Result<>(null, error);
    }

    public S getSuccess() {
        return success;
    }

    public E getError() {
        return error;
    }

    public boolean isSuccess() {
        return success != null;
    }

    public boolean isFailure() {
        return error != null;
    }

    public Response getResponse() {
        return isSuccess() ? success : error;
    }
}
