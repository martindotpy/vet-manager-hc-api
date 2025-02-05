package com.vet.hc.api.product.category.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.product.category.adapter.out.persistence.entity.CategoryEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Repository for categories using Hibernate.
 */
@Component
public class CategoryHibernateRepository implements HibernateRepository<CategoryEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Finds all categories.
     *
     * @return The list of categories found.
     */
    public List<CategoryEntity> findAll() {
        return findAll(entityManager, CategoryEntity.class);
    }

    /**
     * Finds categories by IDs.
     *
     * @param ids The IDs to search by.
     * @return The list of categories found.
     */
    public List<CategoryEntity> findByIds(Iterable<Long> ids) {
        return findByIds(entityManager, CategoryEntity.class, ids);
    }

    /**
     * Finds a category by ID.
     *
     * @param categoryId The category ID to search by.
     * @return The category found.
     */
    public Optional<CategoryEntity> findById(Long categoryId) {
        return findById(entityManager, CategoryEntity.class, categoryId);
    }

    /**
     * Saves a category.
     *
     * @param categoryEntity The category to save.
     * @return The saved category
     */
    @Transactional
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return save(entityManager, categoryEntity);
    }

    /**
     * Deletes a category.
     *
     * @param id The ID of the category to delete.
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, CategoryEntity.class, id);
    }
}
