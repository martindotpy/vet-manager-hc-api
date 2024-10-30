package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.domain.command.CreateCategoryCommand;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a category.
 */
public interface CreateCategoryPort {
    Result<CategoryDto, CategoryFailure> create(CreateCategoryCommand command);
}
