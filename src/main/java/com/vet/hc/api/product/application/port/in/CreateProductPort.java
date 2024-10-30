package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.domain.command.CreateProductCommand;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a product.
 */
public interface CreateProductPort {
    Result<ProductDto, ProductFailure> create(CreateProductCommand command);
}
