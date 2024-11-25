package com.vet.hc.api.bill.productsale.application.port.in;

import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.payload.UpdateProductSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a product sale.
 */
public interface UpdateProductSalePort {
    /**
     * Update a product sale.
     *
     * @param payload Payload with the data to update the product sale.
     * @return Result with the product sale updated or the failure
     */
    Result<ProductSaleDto, ProductSaleFailure> update(UpdateProductSalePayload payload);
}
