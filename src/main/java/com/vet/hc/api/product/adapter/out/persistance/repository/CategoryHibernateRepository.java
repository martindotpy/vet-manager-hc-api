package com.vet.hc.api.product.adapter.out.persistance.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.product.adapter.out.persistance.entity.CategoryEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Repository for categories using Hibernate.
 */
public class CategoryHibernateRepository implements HibernateRepository<CategoryEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all categories.
     *
     * @return The list of categories found.
     */
    public List<CategoryEntity> findAll() {
        return findAll(entityManager, CategoryEntity.class);
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
