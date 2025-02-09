package com.vet.hc.api.user.core.application.dto;

import java.util.List;

import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * User dto.
 *
 * @see com.vet.hc.api.user.core.domain.model.User User
 */
@Getter
@Builder
@ToString
public final class UserDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private List<UserRole> roles;
    private String profileImageUrl;
}
