package com.vet.hc.api.shared.adapter.in.handler;

import com.vet.hc.api.shared.adapter.in.response.FailureResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the {@link UnsupportedOperationException} exception.
 */
@Slf4j
@Provider
public class UnsupportedOperationExceptionHandlerController implements ExceptionMapper<UnsupportedOperationException> {
    @Override
    public Response toResponse(UnsupportedOperationException exception) {
        log.error("Operación no soportada: {}", exception.getMessage(), exception);

        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity(FailureResponse.builder()
                        .message("Operación no soportada")
                        .build())
                .build();
    }
}
