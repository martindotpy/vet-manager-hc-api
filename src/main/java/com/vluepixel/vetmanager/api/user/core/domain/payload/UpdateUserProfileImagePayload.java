package com.vluepixel.vetmanager.api.user.core.domain.payload;

import com.vluepixel.vetmanager.api.image.core.domain.model.enums.ImageMimeType;
import com.vluepixel.vetmanager.api.shared.domain.payload.Payload;

/**
 * Update user profile image payload.
 */
public interface UpdateUserProfileImagePayload extends Payload {
    Long getUserId();

    ImageMimeType getType();

    byte[] getData();
}
