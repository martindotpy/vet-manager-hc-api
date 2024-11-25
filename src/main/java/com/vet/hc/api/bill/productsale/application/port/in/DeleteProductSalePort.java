package com.vet.hc.api.bill.productsale.application.port.in;

import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a product sale by id.
 */
public interface DeleteProductSalePort {
    /**
     * Delete a product sale by id.
     *
     * @param id Id of the product sale to delete.
     * @return Result with the success or the failure
     */
    Result<Void, ProductSaleFailure> deleteById(Long id);
}
