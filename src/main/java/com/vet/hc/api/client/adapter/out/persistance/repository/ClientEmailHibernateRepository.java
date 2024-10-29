package com.vet.hc.api.client.adapter.out.persistance.repository;

import java.util.List;

import com.vet.hc.api.client.adapter.out.persistance.entity.ClientEmailEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Repository for client emails using Hibernate.
 */
public class ClientEmailHibernateRepository {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Saves a client email.
     *
     * @param clientEmailEntity The client email to save.
     * @return The saved client email
     */
    @Transactional
    public ClientEmailEntity save(ClientEmailEntity clientEmailEntity) {
        if (clientEmailEntity.getId() != null)
            return entityManager.merge(clientEmailEntity);

        entityManager.persist(clientEmailEntity);

        return clientEmailEntity;
    }

    /**
     * Deletes a client email.
     *
     * @param id The ID of the client email to delete.
     */
    @Transactional
    public void deleteById(Long id) {
        entityManager
                .createQuery("DELETE FROM ClientEmailEntity c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Finds client emails by client ID.
     *
     * @param clientId The client ID to search by.
     * @return The list of client emails found.
     */
    public List<ClientEmailEntity> findAllByClientId(Long clientId) {
        return entityManager
                .createQuery("SELECT c FROM ClientEmailEntity c WHERE c.client.id = :clientId", ClientEmailEntity.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
}
