package com.vet.hc.api.product.category.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.UpdateCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.product.core.domain.payload.UpdateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case for updating a category.
 */
@UseCase
@RequiredArgsConstructor
public final class UpdateCategoryUseCase implements UpdateCategoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Result<CategoryDto, CategoryFailure> update(UpdateCategoryPayload payload) {
        Category category = Category.builder()
                .id(payload.getId())
                .name(payload.getName())
                .build();

        var result = categoryRepository.save(category);

        if (result.isFailure()) {
            return switch (result.getFailure()) {
                case NOT_FOUND -> Result.failure(CategoryFailure.NOT_FOUND);
                default -> Result.failure(CategoryFailure.UNEXPECTED);
            };
        }

        return Result.success(categoryMapper.toDto(result.getSuccess()));
    }
}
