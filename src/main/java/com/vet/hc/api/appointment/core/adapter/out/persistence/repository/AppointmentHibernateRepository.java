package com.vet.hc.api.appointment.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.core.adapter.out.persistence.entity.AppointmentEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments.
 */
public class AppointmentHibernateRepository implements HibernateRepository<AppointmentEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointments.
     *
     * @return The list of appointments found
     */
    public List<AppointmentEntity> findAll() {
        return findAll(entityManager, AppointmentEntity.class);
    }

    /**
     * Finds an appointment by ID.
     *
     * @param clientId The appointment ID to search by.
     * @return The appointment found
     */
    public Optional<AppointmentEntity> findById(Long clientId) {
        return findById(entityManager, AppointmentEntity.class, clientId);
    }

    /**
     * Saves an appointment.
     *
     * @param appointmentEntity The appointment to save.
     * @return The saved appointment
     */
    @Transactional
    public AppointmentEntity save(AppointmentEntity appointmentEntity) {
        return save(entityManager, appointmentEntity);
    }

    /**
     * Deletes an appointment by ID.
     *
     * @param id The appointment ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, AppointmentEntity.class, id);
    }
}
