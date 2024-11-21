package com.vet.hc.api.product.category.application.port.in;

import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.core.domain.payload.CreateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a category.
 */
public interface CreateCategoryPort {
    Result<CategoryDto, CategoryFailure> create(CreateCategoryPayload payload);
}
