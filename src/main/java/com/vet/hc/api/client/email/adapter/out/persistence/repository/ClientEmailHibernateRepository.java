package com.vet.hc.api.client.email.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.client.email.adapter.out.persistence.entity.ClientEmailEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Repository for client emails using Hibernate.
 */
@Component
public class ClientEmailHibernateRepository implements HibernateRepository<ClientEmailEntity, Long> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Saves a client email.
     *
     * @param clientEmailEntity The client email to save.
     * @return The saved client email
     */
    @Transactional
    public ClientEmailEntity save(ClientEmailEntity clientEmailEntity) {
        return save(entityManager, clientEmailEntity);
    }

    /**
     * Deletes a client email.
     *
     * @param id The ID of the client email to delete.
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, ClientEmailEntity.class, id);
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
