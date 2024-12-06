package com.vet.hc.api.medicalrecord.core.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vet.hc.api.medicalrecord.core.adapter.out.persistence.entity.MedicalRecordEntity;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for medical record.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, TreatmentMapper.class })
public interface MedicalRecordMapper {
    /**
     * Maps the {@link MedicalRecord} domain model to the
     * {@link MedicalRecordDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    MedicalRecordDto toDto(MedicalRecord domain);

    /**
     * Maps the {@link MedicalRecord} domain model to the
     * {@link MedicalRecordEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    MedicalRecordEntity toEntity(MedicalRecord domain);

    /**
     * Maps the {@link MedicalRecordEntity} entity to the
     * {@link MedicalRecord} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "patient", ignore = true)
    MedicalRecord toDomain(MedicalRecordEntity entity);
}
