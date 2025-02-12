package com.vet.hc.api.user.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update email payload.
 */
public interface UpdateUserEmailPayload extends Payload {
    Long getId();

    String getNewEmail();
}
