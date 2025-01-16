package com.vet.hc.api.patient.core.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;

/**
 * Mapper for patient.
 */
@Mapper(componentModel = "spring", uses = {
        RaceMapper.class,
        ClientMapper.class,
        VaccineMapper.class,
        MedicalHistoryMapper.class,
        MedicalRecordMapper.class
})
public interface PatientMapper {
    /**
     * Maps the {@link Patient} domain model to the
     * {@link PatientDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    PatientDto toDto(Patient domain);

    /**
     * Maps the {@link Patient} domain model to the
     * {@link PatientEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    PatientEntity toEntity(Patient domain);

    /**
     * Maps the {@link PatientEntity} entity to the
     * {@link Patient} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Patient toDomain(PatientEntity entity);
}
