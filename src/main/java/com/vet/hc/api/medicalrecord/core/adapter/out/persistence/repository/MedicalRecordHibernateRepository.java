package com.vet.hc.api.medicalrecord.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.medicalrecord.core.adapter.out.persistence.entity.MedicalRecordEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments details.
 */
public class MedicalRecordHibernateRepository implements HibernateRepository<MedicalRecordEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointments details.
     *
     * @return The list of appointments details found
     */
    public List<MedicalRecordEntity> findAll() {
        return findAll(entityManager, MedicalRecordEntity.class);
    }

    /**
     * Finds a medical record by ID.
     *
     * @param clientId The medical record ID to search by.
     * @return The medical record found
     */
    public Optional<MedicalRecordEntity> findById(Long clientId) {
        return findById(entityManager, MedicalRecordEntity.class, clientId);
    }

    /**
     * Saves a medical record.
     *
     * @param medicalrecordEntity The medical record to save.
     * @return The saved medicalrecord
     */
    @Transactional
    public MedicalRecordEntity save(MedicalRecordEntity medicalrecordEntity) {
        return save(entityManager, medicalrecordEntity);
    }

    /**
     * Deletes a medical record by ID.
     *
     * @param id The medical record ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, MedicalRecordEntity.class, id);
    }
}
