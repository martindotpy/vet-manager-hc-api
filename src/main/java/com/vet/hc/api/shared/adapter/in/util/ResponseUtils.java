package com.vet.hc.api.shared.adapter.in.util;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureProvider;
import com.vet.hc.api.shared.domain.query.Failure;
import com.vet.hc.api.shared.domain.query.FailureResponse;
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
     * Create a failure response.
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
     * Create a failure response for a validation error.
     *
     * @param validationErrors the validation errors.
     * @return the response.
     */
    public static Response toValidationFailureResponse(List<ValidationError> validationErrors) {
        checkState(validationErrors != null, "The validation errors cannot be null");

        var details = validationErrors.stream()
                .map(validationError -> "Field `" +
                        validationError.getField() +
                        "`: " +
                        validationError.getMessage())
                .toList();

        log.info("Creating validation failure response with validation errors `{}`", details);

        return Response.status(422)
                .entity(FailureResponse.builder()
                        .message("Bad request")
                        .details(details)
                        .build())
                .build();
    }

    /**
     * Create a failure response.
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
     * Create a failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure, int httpStatus) {
        checkState(failure != null, "The failure cannot be null");

        log.info("Creating failure response with failure `{}` and HTTP status `{}`", failure, httpStatus);

        return Response.status(httpStatus)
                .entity(FailureResponse.builder()
                        .message(failure.getMessage())
                        .build())
                .build();
    }

    /**
     * Create a failure response.
     *
     * @param failure the failure.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure) {
        return toFailureResponse(failure, HttpStatusCodeFailureProvider.get(failure));
    }
}
