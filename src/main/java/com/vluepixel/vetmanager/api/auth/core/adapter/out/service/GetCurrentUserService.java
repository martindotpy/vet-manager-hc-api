package com.vluepixel.vetmanager.api.auth.core.adapter.out.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vluepixel.vetmanager.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

/**
 * Get current user service.
 */
@Service
public final class GetCurrentUserService implements GetCurrentUserPort {
    @Override
    public User get() {
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user instanceof User) {
            return (User) user;
        }

        throw new GetUserWhenDoNotLoggedInException();
    }
}
