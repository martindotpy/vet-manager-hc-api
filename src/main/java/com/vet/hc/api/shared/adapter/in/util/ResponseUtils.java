package com.vet.hc.api.shared.adapter.in.util;

import com.vet.hc.api.shared.domain.query.Failure;
import com.vet.hc.api.shared.domain.query.FailureResponse;

import jakarta.ws.rs.core.Response;

/**
 * Utility class for responses.
 */
public class ResponseUtils {
    /**
     * Create a failure response.
     *
     * @param message    the message.
     * @param httpStatus the HTTP status.
     * @return the response.
     */
    public static Response toFailureResponse(String message, Response.Status httpStatus) {
        return Response.status(httpStatus)
                .entity(FailureResponse.builder()
                        .message(message)
                        .build())
                .build();
    }

    /**
     * Create a failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @return the response.
     */
    public static Response toFailureResponse(Failure failure, Response.Status httpStatus) {
        return Response.status(httpStatus)
                .entity(FailureResponse.builder()
                        .message(failure.getMessage())
                        .build())
                .build();
    }
}
