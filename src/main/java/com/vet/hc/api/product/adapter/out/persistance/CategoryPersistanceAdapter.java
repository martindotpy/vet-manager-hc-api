package com.vet.hc.api.product.adapter.out.persistance;

import java.util.List;

import com.vet.hc.api.product.adapter.out.mapper.CategoryMapper;
import com.vet.hc.api.product.adapter.out.persistance.repository.CategoryHibernateRepository;
import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.product.domain.repository.CategoryRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for category persistance.
 */
@Slf4j
@NoArgsConstructor
public class CategoryPersistanceAdapter implements CategoryRepository {
    private CategoryHibernateRepository categoryHibernateRepository;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Inject
    public CategoryPersistanceAdapter(CategoryHibernateRepository categoryHibernateRepository) {
        this.categoryHibernateRepository = categoryHibernateRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryHibernateRepository.findAll().stream().map(categoryMapper::toDomain).toList();
    }

    @Override
    public Result<Category, RepositoryFailure> save(Category category) {
        try {
            return Result
                    .success(categoryMapper
                            .toDomain(categoryHibernateRepository.save(categoryMapper.toEntity(category))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            categoryHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(RepositoryFailure.NOT_FOUND);
        }
    }
}
