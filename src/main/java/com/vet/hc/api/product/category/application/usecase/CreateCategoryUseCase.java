package com.vet.hc.api.product.category.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.CreateCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.product.core.domain.payload.CreateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case for creating a new category.
 */
@UseCase
@RequiredArgsConstructor
public final class CreateCategoryUseCase implements CreateCategoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Result<CategoryDto, CategoryFailure> create(CreateCategoryPayload payload) {
        Category category = Category.builder()
                .name(payload.getName())
                .build();

        var result = categoryRepository.save(category);

        if (result.isFailure()) {
            return Result.failure(CategoryFailure.DUPLICATE);
        }

        return Result.success(categoryMapper.toDto(result.getSuccess()));
    }
}
