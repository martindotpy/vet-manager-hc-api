package com.vet.hc.api.product.core.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for product failures.
 */
@Component
public final class HttpStatusCodeProductFailureHandler implements HttpStatusCodeFailureHandler<ProductFailure> {
    @Override
    public int getHttpStatusCode(ProductFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATE -> 409;
            case FIELD_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
