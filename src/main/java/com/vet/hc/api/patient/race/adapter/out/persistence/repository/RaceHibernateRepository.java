package com.vet.hc.api.patient.race.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.race.adapter.out.persistence.entity.RaceEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for races.
 */
public class RaceHibernateRepository implements HibernateRepository<RaceEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all races.
     *
     * @return The list of races found
     */
    public List<RaceEntity> findAll() {
        return findAll(entityManager, RaceEntity.class);
    }

    /**
     * Finds a race by ID.
     *
     * @param clientId The race ID to search by.
     * @return The race found
     */
    public Optional<RaceEntity> findById(Long clientId) {
        return findById(entityManager, RaceEntity.class, clientId);
    }

    /**
     * Saves a race.
     *
     * @param raceEntity The race to save.
     * @return The saved race
     */
    @Transactional
    public RaceEntity save(RaceEntity raceEntity) {
        return save(entityManager, raceEntity);
    }

    /**
     * Deletes a race by ID.
     *
     * @param id The race ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, RaceEntity.class, id);
    }
}
