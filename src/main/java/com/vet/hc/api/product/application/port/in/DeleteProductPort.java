package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a product.
 */
public interface DeleteProductPort {
    Result<Void, ProductFailure> deleteById(Long id);
}
