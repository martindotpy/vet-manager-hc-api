package com.vet.hc.api.bill.productsale.domain.repository;

import java.util.Optional;

import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository interface for {@link ProductSale} model.
 */
public interface ProductSaleRepository {
    /**
     * Find a product sale by its id.
     *
     * @param id the product sale id.
     * @return an optional of product sale
     */
    Optional<ProductSale> findById(Long id);

    /**
     * Save a product sale.
     *
     * @param productSale the product sale to save
     * @return Result with the saved product sale or a failure
     */
    Result<ProductSale, ProductSaleFailure> save(ProductSale productSale);

    /**
     * Delete a product sale by its id.
     *
     * @param id the product sale id
     * @return Result with success or a failure
     */
    Result<Void, ProductSaleFailure> deleteById(Long id);
}
