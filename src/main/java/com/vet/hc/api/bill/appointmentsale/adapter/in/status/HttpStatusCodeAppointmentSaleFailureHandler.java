package com.vet.hc.api.bill.appointmentsale.adapter.in.status;

import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for appointment sale failures.
 */
public final class HttpStatusCodeAppointmentSaleFailureHandler
        implements HttpStatusCodeFailureHandler<AppointmentSaleFailure> {
    @Override
    public int getHttpStatusCode(AppointmentSaleFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
