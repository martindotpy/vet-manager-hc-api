package com.vet.hc.api.patient.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;
import com.vet.hc.api.shared.adapter.out.repository.PaginatedHibernateRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;

import jakarta.transaction.Transactional;

/**
 * Hibernate repository for patients.
 */
@Component
public class PatientHibernateRepository
        extends PaginatedHibernateRepository<PatientEntity>
        implements HibernateRepository<PatientEntity, Long> {
    /**
     * Finds all patients.
     *
     * @return The list of patients found
     */
    public List<PatientEntity> findAll() {
        return findAll(entityManager, PatientEntity.class);
    }

    /**
     * Finds all patients that match the given criteria.
     *
     * @param criteria The criteria to match by.
     * @return The list of patients found
     */
    public Paginated<PatientEntity> match(Criteria criteria) {
        return match(criteria, PatientEntity.class);
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
