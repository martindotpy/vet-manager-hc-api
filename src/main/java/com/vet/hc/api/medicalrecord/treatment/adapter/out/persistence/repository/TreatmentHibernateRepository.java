package com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity.TreatmentEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments details.
 */
public class TreatmentHibernateRepository implements HibernateRepository<TreatmentEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointments details.
     *
     * @return The list of appointments details found
     */
    public List<TreatmentEntity> findAll() {
        return findAll(entityManager, TreatmentEntity.class);
    }

    /**
     * Finds a treatment by ID.
     *
     * @param clientId The treatment ID to search by.
     * @return The treatment found
     */
    public Optional<TreatmentEntity> findById(Long clientId) {
        return findById(entityManager, TreatmentEntity.class, clientId);
    }

    /**
     * Saves a treatment.
     *
     * @param medicalrecordEntity The treatment to save.
     * @return The saved medicalrecord
     */
    @Transactional
    public TreatmentEntity save(TreatmentEntity medicalrecordEntity) {
        return save(entityManager, medicalrecordEntity);
    }

    /**
     * Deletes a treatment by ID.
     *
     * @param id The treatment ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, TreatmentEntity.class, id);
    }
}
