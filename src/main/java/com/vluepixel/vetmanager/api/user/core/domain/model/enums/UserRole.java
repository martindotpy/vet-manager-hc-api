package com.vluepixel.vetmanager.api.user.core.domain.model.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Roles of the user.
 */
public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
