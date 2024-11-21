package com.vet.hc.api.product.category.application.port.in;

import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.core.domain.payload.UpdateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a category.
 */
public interface UpdateCategoryPort {
    /**
     * Update a category.
     *
     * @param payload the payload to update a category.
     * @return the updated category
     */
    Result<CategoryDto, CategoryFailure> update(UpdateCategoryPayload payload);
}
