package com.vet.hc.api.user.domain.repository;

import java.util.Optional;

import com.vet.hc.api.user.domain.model.User;

/**
 * Represents a repository for users.
 */
public interface UserRepository {
    /**
     * Saves a user.
     *
     * @param user The user to save.
     * @return The saved user
     */
    User save(User user);

    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);
}
