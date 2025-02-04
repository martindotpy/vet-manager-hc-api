package com.vet.hc.api.auth.core.application.port.out;

import com.vet.hc.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vet.hc.api.user.core.domain.model.User;

/**
 * Get current authenticated user port.
 */
public interface GetCurrentUserPort {
    /**
     * Get current user (logged in user).
     *
     * @return the user
     * @throws GetUserWhenDoNotLoggedInException if the user is not logged in
     */
    User get();
}
