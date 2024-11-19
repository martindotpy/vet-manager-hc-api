package com.vet.hc.api.product.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a category.
 */
public interface CreateCategoryPayload extends Payload {
    String getName();
}
