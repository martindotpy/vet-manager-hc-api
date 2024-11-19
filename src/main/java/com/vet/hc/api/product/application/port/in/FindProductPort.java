package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.adapter.in.response.PaginatedProduct;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding a product.
 */
public interface FindProductPort {
    /**
     * Find a product by its id.
     *
     * @param id The id of the product.
     * @return The product if found, otherwise a failure.
     */
    Result<ProductDto, ProductFailure> findById(Long id);

    /**
     * Find all matching products.
     *
     * @param criteria    The criteria to filter the products.
     * @param categoryIds The category ids to filter the products.
     * @return The matching products
     */
    Result<PaginatedProduct, ProductFailure> match(Criteria criteria, Iterable<Integer> categoryIds);
}
