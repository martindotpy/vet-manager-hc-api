package com.vet.hc.api.bill.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for bills.
 */
public class BillHibernateRepository implements HibernateRepository<BillEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all bills.
     *
     * @return The list of bills found
     */
    public List<BillEntity> findAll() {
        return findAll(entityManager, BillEntity.class);
    }

    /**
     * Finds an bill by ID.
     *
     * @param clientId The bill ID to search by.
     * @return The bill found
     */
    public Optional<BillEntity> findById(Long clientId) {
        return findById(entityManager, BillEntity.class, clientId);
    }

    /**
     * Saves an bill.
     *
     * @param billEntity The bill to save.
     * @return The saved bill
     */
    @Transactional
    public BillEntity save(BillEntity billEntity) {
        return save(entityManager, billEntity);
    }

    /**
     * Deletes an bill by ID.
     *
     * @param id The bill ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, BillEntity.class, id);
    }
}
