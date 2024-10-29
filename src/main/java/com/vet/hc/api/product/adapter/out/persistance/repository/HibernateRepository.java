package com.vet.hc.api.product.adapter.out.persistance.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

/**
 * Repository for Hibernate.
 */
public interface HibernateRepository<T, ID> {
    /**
     * Finds all entities.
     *
     * @param entityManager The entity manager to use.
     * @param entityClass   The entity class to search for.
     * @return The list of entities found
     */
    default List<T> findAll(EntityManager entityManager, Class<T> entityClass) {
        return entityManager
                .createQuery("SELECT c FROM " + entityClass.getSimpleName() + " c", entityClass)
                .getResultList();
    }

    /**
     * Finds an entity by ID.
     *
     * @param entityManager The entity manager to use.
     * @param entityClass   The entity class to search for.
     * @param id            The ID to search by.
     * @return The entity found
     */
    default Optional<T> findById(EntityManager entityManager, Class<T> entityClass, ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Saves an entity.
     *
     * @param entityManager The entity manager to use.
     * @param entity        The entity to save.
     * @return The saved entity
     */
    default T save(EntityManager entityManager, T entity) {
        if (entityManager.contains(entity))
            return entity;

        return entityManager.merge(entity);
    }

    /**
     * Deletes an entity by ID.
     *
     * @param entityManager The entity manager to use.
     * @param entityClass   The entity class to search for.
     * @param id            The ID to search by.
     */
    default void deleteById(EntityManager entityManager, Class<T> entityClass, ID id) {
        T entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }
}
