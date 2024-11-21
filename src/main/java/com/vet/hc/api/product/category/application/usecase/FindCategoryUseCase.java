package com.vet.hc.api.product.category.application.usecase;

import java.util.List;

import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.FindCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Use case to find a category.
 */
@NoArgsConstructor
public class FindCategoryUseCase implements FindCategoryPort {
    private CategoryRepository categoryRepository;

    @Inject
    public FindCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }
}
