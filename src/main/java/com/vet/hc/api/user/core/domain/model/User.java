package com.vet.hc.api.user.core.domain.model;

import java.util.Set;

import com.vet.hc.api.user.core.domain.enums.UserRole;

/**
 * The user domain model as an interface.
 */
public interface User {
    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    Set<UserRole> getRoles();
}
