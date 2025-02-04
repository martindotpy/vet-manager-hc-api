package com.vet.hc.api.shared.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Basic repository.
 *
 * @param <T>  Entity type.
 * @param <ID> Entity id type.
 * @param <F>  Failure type.
 */
public interface BasicRepository<T, ID, F extends Failure> {
    /**
     * Find all entities.
     *
     * @return List of entities
     */
    List<? extends T> findAll();

    /**
     * Find entity by id.
     *
     * @param id Entity id
     * @return Entity
     */
    Optional<? extends T> findById(ID id);

    /**
     * Save entity.
     *
     * @param entity Entity
     * @return Entity if successful, failure otherwise
     */
    Result<? extends T, F> save(T entity);

    /**
     * Delete entity by id.
     *
     * @param id Entity id
     * @return Void if successful, failure otherwise
     */
    Result<Void, F> deleteById(ID id);

    /**
     * Check if entity exists by id.
     *
     * @param id Entity id
     * @return True if entity exists, false otherwise
     */
    boolean existsById(ID id);

    /**
     * Update entity fields by id.
     *
     * @param id           the entity id.
     * @param fieldUpdates the field to update.
     * @return Entity if successful, failure otherwise
     */
    Result<? extends T, F> update(ID id, FieldUpdate necessaryField, FieldUpdate... fieldUpdates);

    /**
     * Update entity fields by id.
     *
     * @param id           the entity id.
     * @param fieldUpdates the fields to update.
     * @return Entity if successful, failure otherwise
     */
    Result<? extends T, F> update(ID id, Collection<FieldUpdate> fieldUpdates);
}
