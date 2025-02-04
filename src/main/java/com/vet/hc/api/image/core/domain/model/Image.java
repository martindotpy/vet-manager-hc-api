package com.vet.hc.api.image.core.domain.model;

import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;

/**
 * Image domain model.
 */
public interface Image {
    Long getId();

    String getFilename();

    ImageMimeType getType();

    byte[] getData();
}
