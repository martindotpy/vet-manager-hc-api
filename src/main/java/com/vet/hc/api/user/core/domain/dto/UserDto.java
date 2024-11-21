package com.vet.hc.api.user.core.domain.dto;

import java.util.Set;

import com.vet.hc.api.user.core.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Set<UserRole> roles;
}
