package com.vet.hc.api.bill.productsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.CreateSalePayload;

/**
 * Payload to create a new product sale.
 */
public interface CreateProductSalePayload extends CreateSalePayload {
    Long getProductId();

    Integer getQuantity();
}
