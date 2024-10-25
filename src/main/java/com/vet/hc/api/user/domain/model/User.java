package com.vet.hc.api.user.domain.model;

import com.vet.hc.api.user.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The user domain model.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
