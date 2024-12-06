package com.vet.hc.api.patient.vaccine.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.patient.vaccine.adapter.out.persistence.entity.VaccineEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments details.
 */
@Component
public class VaccineHibernateRepository implements HibernateRepository<VaccineEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Finds all appointments details.
     *
     * @return The list of appointments details found
     */
    public List<VaccineEntity> findAll() {
        return findAll(entityManager, VaccineEntity.class);
    }

    /**
     * Finds a vaccine by ID.
     *
     * @param clientId The vaccine ID to search by.
     * @return The vaccine found
     */
    public Optional<VaccineEntity> findById(Long clientId) {
        return findById(entityManager, VaccineEntity.class, clientId);
    }

    /**
     * Saves a vaccine.
     *
     * @param vaccineEntity The vaccine to save.
     * @return The saved vaccine
     */
    @Transactional
    public VaccineEntity save(VaccineEntity vaccineEntity) {
        return save(entityManager, vaccineEntity);
    }

    /**
     * Deletes a vaccine by ID.
     *
     * @param id The vaccine ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, VaccineEntity.class, id);
    }
}
