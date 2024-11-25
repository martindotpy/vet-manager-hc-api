package com.vet.hc.api.bill.productsale.adapter.in.status;

import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for product sale failures.
 */
public final class HttpStatusCodeProductSaleFailureHandler
        implements HttpStatusCodeFailureHandler<ProductSaleFailure> {
    @Override
    public int getHttpStatusCode(ProductSaleFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
