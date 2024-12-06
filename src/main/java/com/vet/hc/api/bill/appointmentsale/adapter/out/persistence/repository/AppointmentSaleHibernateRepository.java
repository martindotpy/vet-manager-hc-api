package com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.entity.AppointmentSaleEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for appointment sales.
 */
@Component
public class AppointmentSaleHibernateRepository implements HibernateRepository<AppointmentSaleEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Finds all appointment sales.
     *
     * @return The list of appointment sales found
     */
    public List<AppointmentSaleEntity> findAll() {
        return findAll(entityManager, AppointmentSaleEntity.class);
    }

    /**
     * Finds a appointment sale by ID.
     *
     * @param clientId The appointment sale ID to search by.
     * @return The appointment sale found
     */
    public Optional<AppointmentSaleEntity> findById(Long clientId) {
        return findById(entityManager, AppointmentSaleEntity.class, clientId);
    }

    /**
     * Saves a appointment sale.
     *
     * @param appointmentSaleEntity The appointment sale to save.
     * @return The saved appointment sale
     */
    @Transactional
    public AppointmentSaleEntity save(AppointmentSaleEntity appointmentSaleEntity) {
        return save(entityManager, appointmentSaleEntity);
    }

    /**
     * Deletes a appointment sale by ID.
     *
     * @param id The appointment sale ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, AppointmentSaleEntity.class, id);
    }
}
