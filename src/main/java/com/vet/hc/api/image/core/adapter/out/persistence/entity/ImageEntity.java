package com.vet.hc.api.image.core.adapter.out.persistence.entity;

import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an image entity.
 *
 * @see Image
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ImageEntity implements Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private ImageMimeType type;
    private byte[] data;
}
