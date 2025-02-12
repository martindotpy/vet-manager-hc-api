package com.vluepixel.vetmanager.api.image.core.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Image mime type.
 */
@Getter
@RequiredArgsConstructor
public enum ImageMimeType {
    PNG("image/png"),
    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    WEBP("image/webp");

    private final String value;

    /**
     * Get the image mime type from the given value.
     *
     * @param value the value.
     * @return the image mime type
     */
    public static ImageMimeType fromValue(String value) {
        for (ImageMimeType mimeType : values()) {
            if (mimeType.value.equals(value)) {
                return mimeType;
            }
        }

        throw new IllegalArgumentException("Unknown image mime type: " + value);
    }
}
