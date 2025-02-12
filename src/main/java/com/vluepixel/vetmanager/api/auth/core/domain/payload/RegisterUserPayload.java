package com.vluepixel.vetmanager.api.auth.core.domain.payload;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

/**
 * Register user payload.
 */
public interface RegisterUserPayload extends Payload {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();
}
