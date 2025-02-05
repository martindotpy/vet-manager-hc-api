package com.vet.hc.api.client.core.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vet.hc.api.client.core.adapter.out.persistence.entity.ClientEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;
import com.vet.hc.api.shared.adapter.out.repository.PaginatedHibernateRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;

import jakarta.transaction.Transactional;

/**
 * Repository for clients using Hibernate.
 */
@Component
public class ClientHibernateRepository extends PaginatedHibernateRepository<ClientEntity>
        implements HibernateRepository<ClientEntity, Long> {
    /**
     * Finds all clients.
     *
     * @return The list of clients found
     */
    public List<ClientEntity> findAll() {
        return findAll(entityManager, ClientEntity.class);
    }

    /**
     * Finds a client by ID.
     *
     * @param clientId The client ID to search by.
     * @return The client found
     */
    public Optional<ClientEntity> findById(Long clientId) {
        return findById(entityManager, ClientEntity.class, clientId);
    }

    /**
     * Finds a client by email.
     *
     * @param criteria The criteria to search by.
     * @return The client found
     */
    public Paginated<ClientEntity> match(Criteria criteria) {
        return match(criteria, ClientEntity.class);
    }

    /**
     * Saves a client.
     *
     * @param clientEntity The client to save.
     * @return The saved client
     */
    @Transactional
    public ClientEntity save(ClientEntity clientEntity) {
        return save(entityManager, clientEntity);
    }

    /**
     * Deletes a client.
     *
     * @param id The ID of the client to delete
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, ClientEntity.class, id);
    }
}
