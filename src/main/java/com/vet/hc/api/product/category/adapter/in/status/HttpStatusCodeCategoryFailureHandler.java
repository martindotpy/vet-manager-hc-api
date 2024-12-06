package com.vet.hc.api.product.category.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for product failures.
 */
@Component
public final class HttpStatusCodeCategoryFailureHandler implements HttpStatusCodeFailureHandler<CategoryFailure> {
    @Override
    public int getHttpStatusCode(CategoryFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case DUPLICATE -> 409;
            case FIELD_NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
