package com.vluepixel.vetmanager.api.user.core.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vluepixel.vetmanager.api.user.core.domain.model.User;

/**
 * User spring repository.
 */
public interface UserSpringRepository extends JpaRepository<User, Long> {
    /**
     * Find by email.
     *
     * @param email The email.
     * @return The user entity
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by email and deleted or not.
     *
     * @param email The email.
     * @return The found user
     */
    @Query(value = "SELECT * FROM `user` WHERE email = ?1", nativeQuery = true)
    Optional<User> findByEmailDeletedOrNot(String email);

    /**
     * Restore user by email.
     *
     * @param email The email.
     */
    @Modifying
    @Query(value = "UPDATE `user` SET deleted = false WHERE email = ?1", nativeQuery = true)
    void restoreUserByEmail(String email);
}
