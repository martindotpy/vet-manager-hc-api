package com.vluepixel.vetmanager.api.auth.core.domain.payload;

import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

/**
 * Update password payload.
 */
public interface UpdatePasswordPayload extends Payload {
    String getPassword();

    String getNewPassword();
}
