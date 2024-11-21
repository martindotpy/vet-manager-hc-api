package com.vet.hc.api.product.category.application.usecase;

import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.UpdateCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.product.core.domain.payload.UpdateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Use case for updating a category.
 */
@NoArgsConstructor
public class UpdateCategoryUseCase implements UpdateCategoryPort {
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Inject
    public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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
