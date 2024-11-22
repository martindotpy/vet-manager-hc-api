package com.vet.hc.api.appointment.type.adapter.in.status;

import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for appointment type failures.
 */
public final class HttpStatusCodeAppointmentTypeFailureHandler
        implements HttpStatusCodeFailureHandler<AppointmentTypeFailure> {
    @Override
    public int getHttpStatusCode(AppointmentTypeFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATED_NAME -> 409;
            case UNEXPECTED -> 500;
        };
    }
}
