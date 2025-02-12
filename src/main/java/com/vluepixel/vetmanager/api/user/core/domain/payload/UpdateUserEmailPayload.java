package com.vluepixel.vetmanager.api.user.core.domain.payload;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

/**
 * Update email payload.
 */
public interface UpdateUserEmailPayload extends Payload {
    Long getId();

    String getNewEmail();
}
