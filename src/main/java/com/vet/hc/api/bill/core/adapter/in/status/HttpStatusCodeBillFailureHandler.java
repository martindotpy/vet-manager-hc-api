package com.vet.hc.api.bill.core.adapter.in.status;

import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for appointment type failures.
 */
public final class HttpStatusCodeBillFailureHandler
        implements HttpStatusCodeFailureHandler<BillFailure> {
    @Override
    public int getHttpStatusCode(BillFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATED_NAME -> 409;
            case UNEXPECTED -> 500;
        };
    }
}
