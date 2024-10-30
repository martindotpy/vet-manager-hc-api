package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.application.port.in.DeleteCategoryPort;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.product.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to delete a category.
 */
@NoArgsConstructor
public class DeleteCategoryService implements DeleteCategoryPort {
    private CategoryRepository categoryRepository;

    @Inject
    public DeleteCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Result<Void, CategoryFailure> deleteById(Long id) {
        var result = categoryRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(CategoryFailure.NOT_FOUND);
        }

        return Result.success();
    }
}
