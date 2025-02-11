package com.vet.hc.api.user.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.shared.domain.repository.CriteriaRepository;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;

/**
 * User repository.
 */
public interface UserRepository extends CriteriaRepository<User, Long, UserFailure> {
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
    Result<Void, UserFailure> restoreUserByEmail(String email);
}
