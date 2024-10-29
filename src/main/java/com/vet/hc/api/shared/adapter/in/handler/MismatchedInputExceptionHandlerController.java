package com.vet.hc.api.shared.adapter.in.handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.vet.hc.api.shared.domain.query.FailureResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Handles the {@link MismatchedInputException} exception.
 */
@Provider
public class MismatchedInputExceptionHandlerController implements ExceptionMapper<MismatchedInputException> {
    @Override
    public Response toResponse(MismatchedInputException exception) {
        StringBuilder messageSb = new StringBuilder();
        Response.Status status;
        Reference reference = exception.getPath().getFirst();

        if (reference != null) {
            messageSb.append("El tipo de dato introducido en el campo `")
                    .append(reference.getFieldName())
                    .append("` no es válido");
            status = Response.Status.BAD_REQUEST;
        } else {
            messageSb.append("El tipo de dato introducido no es válido");
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(FailureResponse.builder()
                        .message(messageSb.toString())
                        .build())
                .build();
    }
}
