package com.vet.hc.api.appointment.details.adapter.in.status;

import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for appointment details failures.
 */
public final class HttpStatusCodeAppointmentDetailsFailureHandler
        implements HttpStatusCodeFailureHandler<AppointmentDetailsFailure> {
    @Override
    public int getHttpStatusCode(AppointmentDetailsFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case APPOINTMENT_NOT_FOUND -> 404;
            case APPOINTMENT_TYPE_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
