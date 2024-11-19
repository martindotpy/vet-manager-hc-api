package com.vet.hc.api.product.domain.payload;

import java.util.Set;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update a product.
 */
public interface UpdateProductPayload extends Payload {
    Long getId();

    String getName();

    String getDescription();

    Double getPrice();

    Integer getQuantity();

    Set<Long> getCategoryIds();
}
