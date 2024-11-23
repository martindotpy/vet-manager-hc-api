package com.vet.hc.api.shared.adapter.in.handler;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the {@link MismatchedInputException} exception.
 */
@Slf4j
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

        log.error("Error de mapeo de JSON: {}", exception.getMessage(), exception);

        return toFailureResponse(messageSb.toString(), status);
    }
}
