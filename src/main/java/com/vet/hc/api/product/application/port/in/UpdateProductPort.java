package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.payload.UpdateProductPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a product.
 */
public interface UpdateProductPort {
    /**
     * Update a product.
     *
     * @param payload the payload to update a product.
     * @return the updated product
     */
    Result<ProductDto, ProductFailure> update(UpdateProductPayload payload);
}
