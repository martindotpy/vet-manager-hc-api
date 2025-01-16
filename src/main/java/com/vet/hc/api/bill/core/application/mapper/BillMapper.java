package com.vet.hc.api.bill.core.application.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;

/**
 * Mapper for bill.
 */
@Mapper(componentModel = "spring", uses = {
        RaceMapper.class,
        ClientMapper.class,
        VaccineMapper.class,
        MedicalHistoryMapper.class,
        MedicalRecordMapper.class
})
public interface BillMapper {
    /**
     * Maps the {@link Bill} domain model to the
     * {@link BillDto} DTO.
     *
     * @param domain the domain model to map.
     * @return the DTO
     */
    BillDto toDto(Bill domain);

    /**
     * Maps the {@link Bill} domain model to the
     * {@link BillEntity} entity.
     *
     * @param domain the domain model to map.
     * @return the entity
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    BillEntity toEntity(Bill domain);

    /**
     * Maps the {@link BillEntity} entity to the
     * {@link Bill} domain model.
     *
     * @param entity the entity to map.
     * @return the domain model
     */
    Bill toDomain(BillEntity entity);
}
