package com.vet.hc.api.auth.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Represents a payload to login a user.
 */
public interface LoginUserPayload extends Payload {
    String getEmail();

    String getPassword();
}
