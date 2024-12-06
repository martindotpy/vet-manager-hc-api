package com.vet.hc.api.product.category.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.category.application.port.in.DeleteCategoryPort;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case to delete a category.
 */
@UseCase
@RequiredArgsConstructor
public final class DeleteCategoryUseCase implements DeleteCategoryPort {
    private final CategoryRepository categoryRepository;

    @Override
    public Result<Void, CategoryFailure> deleteById(Long id) {
        var result = categoryRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(CategoryFailure.NOT_FOUND);
        }

        return Result.success();
    }
}
