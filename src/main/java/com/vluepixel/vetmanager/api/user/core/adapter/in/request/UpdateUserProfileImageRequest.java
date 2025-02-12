package com.vluepixel.vetmanager.api.user.core.adapter.in.request;

import com.vluepixel.vetmanager.api.image.core.domain.model.enums.ImageMimeType;
import com.vluepixel.vetmanager.api.user.core.domain.payload.UpdateUserProfileImagePayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update user profile image request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateUserProfileImageRequest implements UpdateUserProfileImagePayload {
    private ImageMimeType type;
    private byte[] data;

    private Long userId;
}
