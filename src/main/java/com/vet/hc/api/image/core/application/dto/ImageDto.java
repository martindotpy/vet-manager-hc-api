package com.vet.hc.api.image.core.application.dto;

import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ImageDto {
    private String filename;
    private ImageMimeType type;
    private String data;
}
