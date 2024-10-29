package com.vet.hc.api.shared.adapter.in.handler;

import com.vet.hc.api.shared.domain.query.FailureResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Handles the {@link UnsupportedOperationException} exception.
 */
@Provider
public class UnsupportedOperationExceptionHandlerController implements ExceptionMapper<UnsupportedOperationException> {
    @Override
    public Response toResponse(UnsupportedOperationException exception) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity(FailureResponse.builder()
                        .message("Operación no soportada")
                        .build())
                .build();
    }
}
