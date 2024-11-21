package com.vet.hc.api.shared.adapter.in.util;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureProvider;
import com.vet.hc.api.shared.domain.query.Failure;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for responses.
 */
@Slf4j
public final class ResponseUtils {
    /**
     * Content response with a message.
     *
     * <p>
     * Use this method to define the type of content response and validate it in
     * compile time.
     * </p>
     *
     * @param <T>             the type of the entity.
     * @param <R>             the type of the content response.
     * @param contentResponse the content response class.
     * @param entity          the entity.
     * @param message         the message.
     * @return the response
     */
    public static <T, R extends ContentResponse<T>> Response toOkResponse(Class<R> contentResponse, T entity,
            String message) {
        return toOkResponse(entity, message);
    }

    /**
     * Content response with a message.
     *
     * @param entity  the entity.
     * @param message the message.
     * @return the response
     */
    public static Response toOkResponse(Object entity, String message) {
        log.info("Creating OK response with message `{}` and entity `{}`", message, entity.getClass().getSimpleName());

        return Response.ok(
                ContentResponse.builder()
                        .message(message)
                        .content(entity)
                        .build())
                .build();
    }

    /**
     * Basic response with a simple message.
     *
     * @param message the message.
     * @return the response
     */
    public static Response toOkResponse(String message) {
        log.info("Creating OK response with message `{}`", message);

        return Response.ok(
                BasicResponse.builder()
                        .message(message)
                        .build())
                .build();
    }

    /**
     * Basic response with a simple message.
     *
     * @param paginated the paginated content.
     * @param message   the message.
     * @return the response
     */
    public static Response toPaginatedResponse(Paginated<?> paginated, String message) {
        log.info("Creating paginated response with message `{}` with entity `{}`", message,
                paginated.getContent().getClass().getSimpleName());

        return Response.ok(
                PaginatedResponse.from(paginated, message))
                .build();
    }

    /**
     * Create a basic failure response.
     *
     * @param message    the message.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the message or HTTP status is null.
     * @return the response.
     */
    public static Response toFailureResponse(String message, Status httpStatus) {
        checkState(message != null, "The message cannot be null");
        checkState(httpStatus != null, "The HTTP status cannot be null");

        log.info("Creating failure response with message `{}` and HTTP status `{}`", message, httpStatus);

        return Response.status(httpStatus)
                .entity(FailureResponse.builder()
                        .message(message)
                        .build())
                .build();
    }

    /**
     * Create a detailed basic failure response.
     *
     * @param validationErrors the validation errors.
     * @return the response.
     */
    public static Response toDetailedFailureResponse(List<ValidationError> validationErrors) {
        checkState(validationErrors != null, "The validation errors cannot be null");

        List<String> details = validationErrors.stream()
                .map(validationError -> validationError.getField() + ": " + validationError.getMessage())
                .toList();

        log.info("Creating validation failure response with validation errors `{}`", details);

        return Response.status(Status.BAD_REQUEST)
                .entity(DetailedFailureResponse.builder()
                        .message("Errores en la consulta")
                        .details(details)
                        .build())
                .build();
    }

    /**
     * Create a basic failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the failure or HTTP status is null.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure, Status httpStatus) {
        checkState(failure != null, "The failure cannot be null");

        return toFailureResponse(failure.getMessage(), httpStatus);
    }

    /**
     * Create a basic failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure, int httpStatus) {
        checkState(failure != null, "The failure cannot be null");

        log.info("Creating failure response of `{}` and HTTP status `{}` for `{}`",
                failure,
                httpStatus,
                failure.getClass().getSimpleName());

        return Response.status(httpStatus)
                .entity(FailureResponse.builder()
                        .message(failure.getMessage())
                        .build())
                .build();
    }

    /**
     * Create a basic failure response.
     *
     * @param failure the failure.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure) {
        return toFailureResponse(failure, HttpStatusCodeFailureProvider.get(failure));
    }
}
