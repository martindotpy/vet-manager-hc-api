package com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.entity.MedicalHistoryEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments details.
 */
@Component
public class MedicalHistoryHibernateRepository implements HibernateRepository<MedicalHistoryEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Finds all appointments details.
     *
     * @return The list of appointments details found
     */
    public List<MedicalHistoryEntity> findAll() {
        return findAll(entityManager, MedicalHistoryEntity.class);
    }

    /**
     * Finds a medical history by ID.
     *
     * @param clientId The medical history ID to search by.
     * @return The medical history found
     */
    public Optional<MedicalHistoryEntity> findById(Long clientId) {
        return findById(entityManager, MedicalHistoryEntity.class, clientId);
    }

    /**
     * Saves a medical history.
     *
     * @param medicalhistoryEntity The medical history to save.
     * @return The saved medicalhistory
     */
    @Transactional
    public MedicalHistoryEntity save(MedicalHistoryEntity medicalhistoryEntity) {
        return save(entityManager, medicalhistoryEntity);
    }

    /**
     * Deletes a medical history by ID.
     *
     * @param id The medical history ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, MedicalHistoryEntity.class, id);
    }
}
