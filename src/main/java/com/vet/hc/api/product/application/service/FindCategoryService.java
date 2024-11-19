package com.vet.hc.api.product.application.service;

import java.util.List;

import com.vet.hc.api.product.adapter.out.mapper.CategoryMapper;
import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.application.port.in.FindCategoryPort;
import com.vet.hc.api.product.domain.repository.CategoryRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to load a category.
 */
@NoArgsConstructor
public class FindCategoryService implements FindCategoryPort {
    private CategoryRepository categoryRepository;

    @Inject
    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }
}
