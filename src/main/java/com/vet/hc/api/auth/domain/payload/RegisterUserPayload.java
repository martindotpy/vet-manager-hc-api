package com.vet.hc.api.auth.domain.payload;

import java.util.Set;

import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.user.domain.enums.UserRole;

/**
 * Represents a payload to register a user.
 */
public interface RegisterUserPayload extends Payload {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    Set<UserRole> getRoles();
}