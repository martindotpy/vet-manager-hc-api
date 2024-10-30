package com.vet.hc.api.product.application.port.in;

import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.domain.command.UpdateCategoryCommand;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a category.
 */
public interface UpdateCategoryPort {
    /**
     * Update a category.
     *
     * @param command the command to update a category.
     * @return the updated category
     */
    Result<CategoryDto, CategoryFailure> update(UpdateCategoryCommand command);
}
