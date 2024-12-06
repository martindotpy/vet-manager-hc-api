package com.vet.hc.api.product.category.application.usecase;

import java.util.List;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.application.port.in.FindCategoryPort;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * Use case to find a category.
 */
@UseCase
@RequiredArgsConstructor
public final class FindCategoryUseCase implements FindCategoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }
}
