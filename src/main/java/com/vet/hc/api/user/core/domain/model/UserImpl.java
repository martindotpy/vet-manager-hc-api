package com.vet.hc.api.user.core.domain.model;

import java.util.List;

import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User implementation. Used to create instances of {@link User}.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserImpl implements User {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserRole> roles;
    private Image profileImage;

    private boolean deleted;
}
