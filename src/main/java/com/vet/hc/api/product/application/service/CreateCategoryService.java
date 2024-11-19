package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.adapter.out.mapper.CategoryMapper;
import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.application.port.in.CreateCategoryPort;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.product.domain.payload.CreateCategoryPayload;
import com.vet.hc.api.product.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for creating a new category.
 */
@NoArgsConstructor
public class CreateCategoryService implements CreateCategoryPort {
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Inject
    public CreateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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
