package com.vet.hc.api.user.core.domain.model;

import java.util.List;

import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

/**
 * User.
 */
public interface User {
    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    List<UserRole> getRoles();

    Image getProfileImage();

    boolean isDeleted();
}
