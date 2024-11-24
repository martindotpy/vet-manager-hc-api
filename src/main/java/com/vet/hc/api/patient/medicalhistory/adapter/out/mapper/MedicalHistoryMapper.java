package com.vet.hc.api.patient.medicalhistory.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.entity.MedicalHistoryEntity;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for medical history.
 */
@Mapper(uses = { UserMapper.class })
public interface MedicalHistoryMapper {
    MedicalHistoryMapper INSTANCE = Mappers.getMapper(MedicalHistoryMapper.class);

    /**
     * Maps the {@link MedicalHistory} domain model to the
     * {@link MedicalHistoryDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    MedicalHistoryDto toDto(MedicalHistory domain);

    /**
     * Maps the {@link MedicalHistory} domain model to the
     * {@link MedicalHistoryEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    MedicalHistoryEntity toEntity(MedicalHistory domain);

    /**
     * Maps the {@link MedicalHistoryEntity} entity to the
     * {@link MedicalHistory} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "patient", ignore = true)
    MedicalHistory toDomain(MedicalHistoryEntity entity);
}
