package com.vet.hc.api.user.application.port.out;

import java.util.Optional;

import com.vet.hc.api.user.domain.model.User;

/**
 * Load the user by fields.
 */
public interface LoadUserPort {
    /**
     * Load the {@link User} by email.
     *
     * @param email The email of the user
     * @return the user if found
     */
    Optional<User> findByEmail(String email);
}
