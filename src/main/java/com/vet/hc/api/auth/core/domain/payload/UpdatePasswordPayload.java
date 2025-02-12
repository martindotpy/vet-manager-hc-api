package com.vet.hc.api.auth.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update password payload.
 */
public interface UpdatePasswordPayload extends Payload {
    String getPassword();

    String getNewPassword();
}
