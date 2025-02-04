package com.vet.hc.api.image.core.application.mapper;

import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.image.core.adapter.out.persistence.entity.ImageEntity;
import com.vet.hc.api.image.core.application.dto.ImageDto;
import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.image.core.domain.model.Image;
import com.vet.hc.api.image.core.domain.model.ImageImpl;
import com.vet.hc.api.shared.application.mapper.BasicMapper;

/**
 * Image mapper.
 *
 * <p>
 * Maps the {@link ImageImpl} domain model to the {@link ImageEntity} entity and
 * vice versa.
 * </p>
 *
 * <p>
 * Also maps the {@link ImageImpl} domain model to the {@link ImageDto} DTO.
 * </p>
 *
 * @see Image
 * @see ImageImpl
 * @see ImageEntity
 * @see ImageDto
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends BasicMapper<Image, ImageImpl, ImageEntity, ImageDto, ImageFailure> {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    /**
     * Maps the byte array of the data to a string.
     *
     * @param data the byte array to map.
     * @return the string
     */
    default String mapDataToData(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
