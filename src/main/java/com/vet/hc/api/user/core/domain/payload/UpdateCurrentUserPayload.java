package com.vet.hc.api.user.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update current user payload.
 */
public interface UpdateCurrentUserPayload extends Payload {
    String getFirstName();

    String getLastName();
}
