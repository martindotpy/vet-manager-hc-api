package com.vet.hc.api.patient.species.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.patient.species.adapter.out.persistence.entity.SpeciesEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for species.
 */
@Component
public class SpeciesHibernateRepository implements HibernateRepository<SpeciesEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Finds all species.
     *
     * @return The list of species found
     */
    public List<SpeciesEntity> findAll() {
        return findAll(entityManager, SpeciesEntity.class);
    }

    /**
     * Finds a species by ID.
     *
     * @param clientId The species ID to search by.
     * @return The species found
     */
    public Optional<SpeciesEntity> findById(Long clientId) {
        return findById(entityManager, SpeciesEntity.class, clientId);
    }

    /**
     * Saves a species.
     *
     * @param speciesEntity The species to save.
     * @return The saved species
     */
    @Transactional
    public SpeciesEntity save(SpeciesEntity speciesEntity) {
        return save(entityManager, speciesEntity);
    }

    /**
     * Deletes a species by ID.
     *
     * @param id The species ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, SpeciesEntity.class, id);
    }
}
