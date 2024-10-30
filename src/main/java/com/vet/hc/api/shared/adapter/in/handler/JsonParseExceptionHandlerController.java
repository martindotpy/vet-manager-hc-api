package com.vet.hc.api.shared.adapter.in.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.vet.hc.api.shared.domain.query.FailureResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the {@link JsonParseException} exception.
 */
@Slf4j
@Provider
public class JsonParseExceptionHandlerController implements ExceptionMapper<JsonParseException> {
    @Override
    public Response toResponse(JsonParseException exception) {
        StringBuilder messageSb = new StringBuilder();
        Response.Status status;

        if (exception.getLocation() != null) {
            var reference = exception.getLocation();

            messageSb.append("Error en el JSON en la línea: ")
                    .append(reference.getLineNr())
                    .append(", columna: ")
                    .append(reference.getColumnNr());

            status = Response.Status.BAD_REQUEST;
        } else {
            messageSb.append("El tipo de dato introducido no es válido");
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        log.error("Error de mapeo de JSON: {}", exception.getMessage(), exception);

        return Response.status(status)
                .entity(FailureResponse.builder()
                        .message(messageSb.toString())
                        .build())
                .build();
    }
}