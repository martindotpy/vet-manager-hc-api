package com.vet.hc.api.patient.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for patients.
 */
public class PatientHibernateRepository implements HibernateRepository<PatientEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all patients.
     *
     * @return The list of patients found
     */
    public List<PatientEntity> findAll() {
        return findAll(entityManager, PatientEntity.class);
    }

    /**
     * Finds an patient by ID.
     *
     * @param clientId The patient ID to search by.
     * @return The patient found
     */
    public Optional<PatientEntity> findById(Long clientId) {
        return findById(entityManager, PatientEntity.class, clientId);
    }

    /**
     * Saves an patient.
     *
     * @param patientEntity The patient to save.
     * @return The saved patient
     */
    @Transactional
    public PatientEntity save(PatientEntity patientEntity) {
        return save(entityManager, patientEntity);
    }

    /**
     * Deletes an patient by ID.
     *
     * @param id The patient ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, PatientEntity.class, id);
    }
}
