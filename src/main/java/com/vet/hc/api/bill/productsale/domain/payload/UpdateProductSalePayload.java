package com.vet.hc.api.bill.productsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.UpdateSalePayload;

/**
 * Payload for updating a product sale.
 */
public interface UpdateProductSalePayload extends UpdateSalePayload {
    Long getId();

    Long getProductId();

    Integer getQuantity();
}
