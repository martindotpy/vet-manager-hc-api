package com.vet.hc.api.bill.treatmentsale.adapter.in.status;

import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for treatment sale failures.
 */
public final class HttpStatusCodeTreatmentSaleFailureHandler
        implements HttpStatusCodeFailureHandler<TreatmentSaleFailure> {
    @Override
    public int getHttpStatusCode(TreatmentSaleFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
