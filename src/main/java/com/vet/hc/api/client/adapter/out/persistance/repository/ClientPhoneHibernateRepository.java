package com.vet.hc.api.client.adapter.out.persistance.repository;

import java.util.List;

import com.vet.hc.api.client.adapter.out.persistance.entity.ClientPhoneEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Repository for client phones using Hibernate.
 */
public class ClientPhoneHibernateRepository implements HibernateRepository<ClientPhoneEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Saves a client phone.
     *
     * @param clientPhoneEntity The client phone to save.
     * @return The saved client phone
     */
    @Transactional
    public ClientPhoneEntity save(ClientPhoneEntity clientPhoneEntity) {
        return save(entityManager, clientPhoneEntity);
    }

    /**
     * Deletes a client phone.
     *
     * @param id The ID of the client phone to delete.
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, ClientPhoneEntity.class, id);
    }

    /**
     * Finds client phones by client ID.
     *
     * @param clientId The client ID to search by.
     * @return The list of client phones found.
     */
    public List<ClientPhoneEntity> findAllByClientId(Long clientId) {
        return entityManager
                .createQuery("SELECT c FROM ClientPhoneEntity c WHERE c.client.id = :clientId", ClientPhoneEntity.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
}
