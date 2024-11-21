package com.vet.hc.api.product.category.application.usecase;

import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.CreateCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.product.core.domain.payload.CreateCategoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Use case for creating a new category.
 */
@NoArgsConstructor
public class CreateCategoryUseCase implements CreateCategoryPort {
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Inject
    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
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
