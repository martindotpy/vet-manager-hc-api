package com.vet.hc.api.image.core.adapter.out.persistence;

import com.vet.hc.api.image.core.adapter.out.persistence.entity.ImageEntity;
import com.vet.hc.api.image.core.adapter.out.persistence.repository.ImageSpringRepository;
import com.vet.hc.api.image.core.application.dto.ImageDto;
import com.vet.hc.api.image.core.application.mapper.ImageMapper;
import com.vet.hc.api.image.core.domain.failure.ImageExceptionFailureHandler;
import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.image.core.domain.model.ImageImpl;
import com.vet.hc.api.image.core.domain.repository.ImageRepository;
import com.vet.hc.api.shared.adapter.out.persistence.EntityPersistenceAdapter;
import com.vet.hc.api.shared.application.annotations.PersistenceAdapter;

/**
 * Image persistence adapter.
 */
@PersistenceAdapter
public final class ImagePersistenceAdapter
        extends
        EntityPersistenceAdapter<Image, Long, ImageImpl, ImageEntity, ImageDto, ImageFailure, ImageSpringRepository>
        implements
        ImageRepository {
    public ImagePersistenceAdapter(
            ImageSpringRepository imageSpringRepository,
            ImageExceptionFailureHandler imageExceptionFailureHandler,
            ImageMapper imageMapper) {
        super(imageSpringRepository, imageExceptionFailureHandler, imageMapper);
    }
}
