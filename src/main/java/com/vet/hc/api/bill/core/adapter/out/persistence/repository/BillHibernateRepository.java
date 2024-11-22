package com.vet.hc.api.bill.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointment types.
 */
public class BillHibernateRepository implements HibernateRepository<BillEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointment types.
     *
     * @return The list of appointment types found
     */
    public List<BillEntity> findAll() {
        return findAll(entityManager, BillEntity.class);
    }

    /**
     * Finds an appointment type by ID.
     *
     * @param clientId The appointment type ID to search by.
     * @return The appointment type found
     */
    public Optional<BillEntity> findById(Long clientId) {
        return findById(entityManager, BillEntity.class, clientId);
    }

    /**
     * Saves an appointment type.
     *
     * @param billEntity The appointment type to save.
     * @return The saved appointment type
     */
    @Transactional
    public BillEntity save(BillEntity billEntity) {
        return save(entityManager, billEntity);
    }

    /**
     * Deletes an appointment type by ID.
     *
     * @param id The appointment type ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, BillEntity.class, id);
    }
}
