package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.adapter.out.mapper.CategoryMapper;
import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.application.port.in.UpdateCategoryPort;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.product.domain.payload.UpdateCategoryPayload;
import com.vet.hc.api.product.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for updating a category.
 */
@NoArgsConstructor
public class UpdateCategoryService implements UpdateCategoryPort {
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Inject
    public UpdateCategoryService(CategoryRepository categoryRepository) {
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
