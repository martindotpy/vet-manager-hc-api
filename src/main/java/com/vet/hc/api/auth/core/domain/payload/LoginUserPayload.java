package com.vet.hc.api.auth.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Login user payload.
 */
public interface LoginUserPayload extends Payload {
    String getEmail();

    String getPassword();
}
