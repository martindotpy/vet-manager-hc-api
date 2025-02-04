package com.vet.hc.api.auth.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Register user payload.
 */
public interface RegisterUserPayload extends Payload {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();
}
