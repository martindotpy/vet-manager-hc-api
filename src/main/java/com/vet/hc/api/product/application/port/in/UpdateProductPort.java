package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.domain.command.UpdateProductCommand;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a product.
 */
public interface UpdateProductPort {
    /**
     * Update a product.
     *
     * @param command the command to update a product.
     * @return the updated product
     */
    Result<ProductDto, ProductFailure> update(UpdateProductCommand command);
}
