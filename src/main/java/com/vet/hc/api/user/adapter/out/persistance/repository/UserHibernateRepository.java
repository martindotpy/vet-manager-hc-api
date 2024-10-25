package com.vet.hc.api.user.adapter.out.persistance.repository;

import java.util.Optional;

import com.vet.hc.api.user.adapter.out.persistance.model.UserEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Repository for users using Hibernate.
 */
@Named
@ApplicationScoped
public class UserHibernateRepository {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Saves a user.
     *
     * @param userEntity The user to save.
     * @return The saved user
     */
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        entityManager.persist(userEntity);
        entityManager.flush();

        return userEntity;
    }

    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
    @Transactional
    public Optional<UserEntity> findByEmail(String email) {
        try {
            UserEntity userEntity = entityManager
                    .createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(userEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
