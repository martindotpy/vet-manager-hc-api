package com.vet.hc.api.bill.core.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for bill details failures.
 */
@Component
public final class HttpStatusCodeBillFailureHandler implements HttpStatusCodeFailureHandler<BillFailure> {
    @Override
    public int getHttpStatusCode(BillFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case FIELD_NOT_FOUND -> 404;
            case CLIENT_NOT_FOUND -> 404;
            case CANNOT_UPDATE_PAID_BILL -> 400;
            case UNEXPECTED -> 500;
        };
    }
}
