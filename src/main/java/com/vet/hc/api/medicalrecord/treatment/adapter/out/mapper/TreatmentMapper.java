package com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity.TreatmentEntity;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for treatment.
 */
@Mapper(uses = { UserMapper.class })
public interface TreatmentMapper {
    TreatmentMapper INSTANCE = Mappers.getMapper(TreatmentMapper.class);

    /**
     * Maps the {@link Treatment} domain model to the
     * {@link TreatmentDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    TreatmentDto toDto(Treatment domain);

    /**
     * Maps the {@link Treatment} domain model to the
     * {@link TreatmentEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    TreatmentEntity toEntity(Treatment domain);

    /**
     * Maps the {@link TreatmentEntity} entity to the
     * {@link Treatment} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Treatment toDomain(TreatmentEntity entity);
}
