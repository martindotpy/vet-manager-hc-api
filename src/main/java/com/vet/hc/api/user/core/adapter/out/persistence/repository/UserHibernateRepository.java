package com.vet.hc.api.user.core.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

/**
 * Repository for users using Hibernate.
 */
@Component
public class UserHibernateRepository {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Saves a user.
     *
     * @param userEntity The user to save.
     * @return The saved user
     */
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        entityManager.persist(userEntity);

        return userEntity;
    }

    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
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

    /**
     * Checks if an admin user exists.
     *
     * @return True if an admin user exists, false otherwise.
     */
    public boolean adminExists() {
        return entityManager
                .createQuery("SELECT COUNT(u) FROM UserEntity u JOIN u.roles r WHERE r = 'ADMIN'", Long.class)
                .getSingleResult() > 0;
    }
}
