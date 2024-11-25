package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.productsale.domain.payload.CreateProductSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add product to bill port.
 */
public interface AddProductSaleToBillPort {
    /**
     * Add a product to a bill.
     *
     * @param payload The payload with the data to create the product.
     * @return The result of the operation
     */
    Result<BillDto, BillFailure> add(CreateProductSalePayload payload);
}
