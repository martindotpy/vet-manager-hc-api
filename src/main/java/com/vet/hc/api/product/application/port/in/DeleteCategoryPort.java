package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a category.
 */
public interface DeleteCategoryPort {
    Result<Void, CategoryFailure> deleteById(Long id);
}
