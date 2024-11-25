package com.vet.hc.api.bill.productsale.application.port.in;

import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the product sale.
 */
public interface FindProductSalePort {
    /**
     * Find the product sale by id.
     *
     * @param id the product sale id.
     * @return the product sale found or the failure
     */
    Result<ProductSaleDto, ProductSaleFailure> findById(Long id);
}
