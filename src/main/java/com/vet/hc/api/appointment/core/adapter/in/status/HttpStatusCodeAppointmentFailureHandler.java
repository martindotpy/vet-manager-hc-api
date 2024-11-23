package com.vet.hc.api.appointment.core.adapter.in.status;

import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for appointment details failures.
 */
public final class HttpStatusCodeAppointmentFailureHandler
        implements HttpStatusCodeFailureHandler<AppointmentFailure> {
    @Override
    public int getHttpStatusCode(AppointmentFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case FIELD_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
