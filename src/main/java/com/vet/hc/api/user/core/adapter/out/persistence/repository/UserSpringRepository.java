package com.vet.hc.api.user.core.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vet.hc.api.user.core.adapter.out.persistence.entity.UserEntity;

/**
 * User spring repository.
 */
public interface UserSpringRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Find by email.
     *
     * @param email The email.
     * @return The user entity
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find user by email and deleted or not.
     *
     * @param email The email.
     * @return The found user
     */
    @Query(value = "SELECT * FROM `user` WHERE email = ?1", nativeQuery = true)
    Optional<UserEntity> findByEmailDeletedOrNot(String email);

    /**
     * Restore user by email.
     *
     * @param email The email.
     */
    @Modifying
    @Query(value = "UPDATE `user` SET deleted = false WHERE email = ?1", nativeQuery = true)
    void restoreUserByEmail(String email);
}
