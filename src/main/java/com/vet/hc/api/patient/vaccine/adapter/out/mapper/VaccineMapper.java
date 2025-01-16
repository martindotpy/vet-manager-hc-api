package com.vet.hc.api.patient.vaccine.adapter.out.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.patient.vaccine.adapter.out.persistence.entity.VaccineEntity;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.user.core.application.mapper.UserMapper;

/**
 * Mapper for vaccine.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface VaccineMapper {
    /**
     * Maps the {@link Vaccine} domain model to the
     * {@link VaccineDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    VaccineDto toDto(Vaccine domain);

    /**
     * Maps the {@link Vaccine} domain model to the
     * {@link VaccineEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    VaccineEntity toEntity(Vaccine domain);

    /**
     * Maps the {@link VaccineEntity} entity to the
     * {@link Vaccine} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    @Mapping(target = "patient", ignore = true)
    Vaccine toDomain(VaccineEntity entity);
}
