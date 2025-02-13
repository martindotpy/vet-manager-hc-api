package com.vluepixel.vetmanager.api.user.core.domain.repository;

import java.util.Optional;

import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

/**
 * User repository.
 */
public interface UserRepository extends CriteriaRepository<User, Long> {
    /**
     * Find user by email.
     *
     * @param email The email.
     * @return The found user
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by email and deleted or not.
     *
     * @param email The email.
     * @return The found user
     */
    Optional<User> findByEmailDeletedOrNot(String email);

    /**
     * Restore user by email.
     *
     * @param email The email.
     * @return The result. Void if success, failure otherwise.
     */
    void restoreUserByEmail(String email);
}
