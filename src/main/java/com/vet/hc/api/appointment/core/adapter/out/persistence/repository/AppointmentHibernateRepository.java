package com.vet.hc.api.appointment.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vet.hc.api.appointment.core.adapter.out.persistence.entity.AppointmentEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;
import com.vet.hc.api.shared.adapter.out.repository.PaginatedHibernateRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;

import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointments.
 */
@Component
public class AppointmentHibernateRepository extends PaginatedHibernateRepository<AppointmentEntity>
        implements HibernateRepository<AppointmentEntity, Long> {
    /**
     * Finds all appointments.
     *
     * @return The list of appointments found
     */
    public List<AppointmentEntity> findAll() {
        return findAll(entityManager, AppointmentEntity.class);
    }

    /**
     * Finds all appointments that match the given criteria.
     *
     * @param criteria The criteria to match by.
     * @return The list of appointments found
     */
    public Paginated<AppointmentEntity> match(Criteria criteria) {
        return match(criteria, AppointmentEntity.class);
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
