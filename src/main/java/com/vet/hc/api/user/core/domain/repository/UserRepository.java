package com.vet.hc.api.user.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

/**
 * Represents a repository for users.
 */
public interface UserRepository {
    /**
     * Saves a user.
     *
     * @param user The user to save.
     * @return The saved user if successful, the failure otherwise. The failure can
     *         be:
     *         <ul>
     *         <li>{@link RepositoryFailure#DUPLICATE} if the email is already in
     *         use.</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred while saving the user.</li>
     *         </ul>
     */
    Result<User, RepositoryFailure> save(User user);

    /**
     * Finds a user by email.
     *
     * @param email The email to search for.
     * @return The user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if an admin user exists.
     *
     * @return True if an admin user exists, false otherwise.
     */
    boolean adminExists();
}
