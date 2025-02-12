package com.vluepixel.vetmanager.api.auth.core.domain.payload;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

/**
 * Login user payload.
 */
public interface LoginUserPayload extends Payload {
    String getEmail();

    String getPassword();
}
