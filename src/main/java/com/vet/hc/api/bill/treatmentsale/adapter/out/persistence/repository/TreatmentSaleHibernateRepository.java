package com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.entity.TreatmentSaleEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for treatment sales.
 */
public class TreatmentSaleHibernateRepository implements HibernateRepository<TreatmentSaleEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all treatment sales.
     *
     * @return The list of treatment sales found
     */
    public List<TreatmentSaleEntity> findAll() {
        return findAll(entityManager, TreatmentSaleEntity.class);
    }

    /**
     * Finds a treatment sale by ID.
     *
     * @param clientId The treatment sale ID to search by.
     * @return The treatment sale found
     */
    public Optional<TreatmentSaleEntity> findById(Long clientId) {
        return findById(entityManager, TreatmentSaleEntity.class, clientId);
    }

    /**
     * Saves a treatment sale.
     *
     * @param treatmentSaleEntity The treatment sale to save.
     * @return The saved treatment sale
     */
    @Transactional
    public TreatmentSaleEntity save(TreatmentSaleEntity treatmentSaleEntity) {
        return save(entityManager, treatmentSaleEntity);
    }

    /**
     * Deletes a treatment sale by ID.
     *
     * @param id The treatment sale ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, TreatmentSaleEntity.class, id);
    }
}
