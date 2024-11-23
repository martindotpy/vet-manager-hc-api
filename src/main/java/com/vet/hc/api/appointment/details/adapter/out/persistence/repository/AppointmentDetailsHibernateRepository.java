package com.vet.hc.api.appointment.details.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.details.adapter.out.persistence.entity.AppointmentDetailsEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments details.
 */
public class AppointmentDetailsHibernateRepository implements HibernateRepository<AppointmentDetailsEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all appointments details.
     *
     * @return The list of appointments details found
     */
    public List<AppointmentDetailsEntity> findAll() {
        return findAll(entityManager, AppointmentDetailsEntity.class);
    }

    /**
     * Finds an appointment details by ID.
     *
     * @param clientId The appointment details ID to search by.
     * @return The appointment details found
     */
    public Optional<AppointmentDetailsEntity> findById(Long clientId) {
        return findById(entityManager, AppointmentDetailsEntity.class, clientId);
    }

    /**
     * Saves an appointment details.
     *
     * @param appointmentDetailsEntity The appointment details to save.
     * @return The saved appointment details
     */
    @Transactional
    public AppointmentDetailsEntity save(AppointmentDetailsEntity appointmentDetailsEntity) {
        return save(entityManager, appointmentDetailsEntity);
    }

    /**
     * Deletes an appointment details by ID.
     *
     * @param id The appointment details ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, AppointmentDetailsEntity.class, id);
    }
}
