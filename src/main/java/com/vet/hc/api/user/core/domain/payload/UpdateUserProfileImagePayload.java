package com.vet.hc.api.user.core.domain.payload;

import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update user profile image payload.
 */
public interface UpdateUserProfileImagePayload extends Payload {
    Long getUserId();

    ImageMimeType getType();

    byte[] getData();
}
