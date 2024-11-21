package com.vet.hc.api.product.core.application.port.in;

import com.vet.hc.api.product.category.domain.payload.CreateProductPayload;
import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a product.
 */
public interface CreateProductPort {
    Result<ProductDto, ProductFailure> create(CreateProductPayload payload);
}
