package com.vet.hc.api.image.core.adapter.in.status;

import org.springframework.stereotype.Component;

import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * HTTP status code image failure handler.
 */
@Component
public class HttpStatusCodeImageFailureHandler implements HttpStatusCodeFailureHandler<ImageFailure> {
    @Override
    public int getHttpStatusCode(ImageFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
