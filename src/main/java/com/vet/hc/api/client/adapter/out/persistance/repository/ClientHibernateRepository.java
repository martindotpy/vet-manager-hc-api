package com.vet.hc.api.client.adapter.out.persistance.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ClientHibernateRepository {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;
}
