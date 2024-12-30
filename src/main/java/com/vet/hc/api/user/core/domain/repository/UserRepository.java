package com.vet.hc.api.user.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;

/**
 * Represents a repository for users.
 */
public interface UserRepository {
    /**
     * Saves a user.
     *
     * @param user The user to save.
     * @return The saved user if successful, the failure otherwise.
     */
    Result<User, UserFailure> save(User user);

    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
    Optional<? extends User> findByEmail(String email);

    /**
     * Checks if an admin user exists.
     *
     * @return True if an admin user exists, false otherwise.
     */
    boolean adminExists();
}
