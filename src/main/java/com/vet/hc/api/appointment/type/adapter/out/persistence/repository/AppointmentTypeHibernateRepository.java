package com.vet.hc.api.appointment.type.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.type.adapter.out.persistence.entity.AppointmentTypeEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointment types.
 */
public class AppointmentTypeHibernateRepository implements HibernateRepository<AppointmentTypeEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointment types.
     *
     * @return The list of appointment types found
     */
    public List<AppointmentTypeEntity> findAll() {
        return findAll(entityManager, AppointmentTypeEntity.class);
    }

    /**
     * Finds an appointment type by ID.
     *
     * @param clientId The appointment type ID to search by.
     * @return The appointment type found
     */
    public Optional<AppointmentTypeEntity> findById(Long clientId) {
        return findById(entityManager, AppointmentTypeEntity.class, clientId);
    }

    /**
     * Saves an appointment type.
     *
     * @param appointmentTypeEntity The appointment type to save.
     * @return The saved appointment type
     */
    @Transactional
    public AppointmentTypeEntity save(AppointmentTypeEntity appointmentTypeEntity) {
        return save(entityManager, appointmentTypeEntity);
    }

    /**
     * Deletes an appointment type by ID.
     *
     * @param id The appointment type ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, AppointmentTypeEntity.class, id);
    }
}
