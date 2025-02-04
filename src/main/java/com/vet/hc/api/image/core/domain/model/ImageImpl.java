package com.vet.hc.api.image.core.domain.model;

import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Image implementation. Used to create instances of {@link Image}.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ImageImpl implements Image {
    private Long id;

    private String filename;
    private ImageMimeType type;
    private byte[] data;
}
