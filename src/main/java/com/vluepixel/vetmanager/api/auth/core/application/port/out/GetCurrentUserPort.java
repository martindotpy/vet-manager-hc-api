package com.vluepixel.vetmanager.api.auth.core.application.port.out;

import com.vluepixel.vetmanager.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

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
