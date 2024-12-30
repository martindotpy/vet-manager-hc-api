package com.vet.hc.api.user.core.domain.model;

import java.util.Set;

import com.vet.hc.api.user.core.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The user domain model as an implementation.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserImpl implements User {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<UserRole> roles;
}