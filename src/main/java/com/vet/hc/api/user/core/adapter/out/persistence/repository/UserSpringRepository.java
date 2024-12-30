package com.vet.hc.api.user.core.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

/**
 * Repository for users using Hibernate.
 */
public interface UserSpringRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks if an admin user exists.
     *
     * @return True if an admin user exists, false otherwise.
     */
    @Query("SELECT COUNT(u) > 0 FROM UserEntity u JOIN u.roles r WHERE r = 'ADMIN'")
    boolean adminExists();
}
