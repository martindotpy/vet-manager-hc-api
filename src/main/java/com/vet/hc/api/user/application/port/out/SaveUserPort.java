package com.vet.hc.api.user.application.port.out;

import com.vet.hc.api.user.domain.model.User;

/**
 * Save the user.
 */
public interface SaveUserPort {
    /**
     * Save the {@link User}.
     *
     * @param user The user to save
     * @return the saved user
     */
    User save(User user);
}
