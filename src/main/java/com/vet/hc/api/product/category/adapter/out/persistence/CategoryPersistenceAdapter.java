package com.vet.hc.api.product.category.adapter.out.persistence;

import java.util.List;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.product.category.adapter.out.persistence.repository.CategoryHibernateRepository;
import com.vet.hc.api.product.category.application.mapper.CategoryMapper;
import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for category persistence.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class CategoryPersistenceAdapter implements CategoryRepository {
    private final CategoryHibernateRepository categoryHibernateRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryHibernateRepository.findAll().stream().map(categoryMapper::toDomain).toList();
    }

    @Override
    public Result<Category, CategoryFailure> save(Category category) {
        try {
            return Result
                    .success(categoryMapper
                            .toDomain(categoryHibernateRepository.save(categoryMapper.toEntity(category))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(CategoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, CategoryFailure> deleteById(Long id) {
        try {
            categoryHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(CategoryFailure.NOT_FOUND);
        }
    }
}
