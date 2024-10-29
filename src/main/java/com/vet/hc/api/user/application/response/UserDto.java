package com.vet.hc.api.user.application.response;

import java.util.Set;

import com.vet.hc.api.user.domain.enums.UserRole;

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
