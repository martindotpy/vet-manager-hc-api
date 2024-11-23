package com.vet.hc.api.shared.adapter.in.handler;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;

import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the {@link JsonMappingException} exception.
 */
@Slf4j
@Provider
public class JsonMappingExceptionHandlerController implements ExceptionMapper<JsonMappingException> {
    @Override
    public Response toResponse(JsonMappingException exception) {
        StringBuilder messageSb = new StringBuilder();
        Response.Status status;

        if (exception.getPath() != null) {
            JsonMappingException.Reference reference = exception.getPath().get(0);
            messageSb.append("El tipo de dato introducido en el campo `")
                    .append(reference.getFieldName())
                    .append("` en la línea: ")
                    .append(exception.getLocation().getLineNr())
                    .append(", columna: ")
                    .append(exception.getLocation().getColumnNr())
                    .append(" no es válido");

            status = Response.Status.BAD_REQUEST;
        } else {
            messageSb.append("El tipo de dato introducido no es válido");
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        log.error("Error de mapeo de JSON: {}", exception.getMessage(), exception);

        return toFailureResponse(messageSb.toString(), status);
    }
}
