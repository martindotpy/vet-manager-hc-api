package com.vet.hc.api.product.domain.payload;

import java.util.Set;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a product.
 */
public interface CreateProductPayload extends Payload {
    String getName();

    Double getPrice();

    String getDescription();

    Integer getQuantity();

    Set<Long> getCategoryIds();
}
