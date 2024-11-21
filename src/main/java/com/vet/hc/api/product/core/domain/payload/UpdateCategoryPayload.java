package com.vet.hc.api.product.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update a category.
 */
public interface UpdateCategoryPayload extends Payload {
    Long getId();

    String getName();
}
