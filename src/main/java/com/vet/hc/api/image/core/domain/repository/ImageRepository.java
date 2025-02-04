package com.vet.hc.api.image.core.domain.repository;

import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.shared.domain.repository.BasicRepository;

/**
 * Image repository.
 */
public interface ImageRepository extends BasicRepository<Image, Long, ImageFailure> {
}
