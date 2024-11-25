package com.vet.hc.api.bill.productsale.application.port.in;

import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.payload.CreateProductSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a product sale.
 */
public interface CreateProductSalePort {
    /**
     * Create a product sale.
     *
     * @param payload Payload with the data to create the product sale.
     * @return Result with the product sale created or the failure
     */
    Result<ProductSaleDto, ProductSaleFailure> create(CreateProductSalePayload payload);
}
