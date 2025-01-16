package com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity.TreatmentEntity;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for treatment.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TreatmentMapper {
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
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    TreatmentEntity toEntity(Treatment domain);

    /**
     * Maps the {@link TreatmentEntity} entity to the
     * {@link Treatment} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "medicalRecord", ignore = true)
    Treatment toDomain(TreatmentEntity entity);
}
