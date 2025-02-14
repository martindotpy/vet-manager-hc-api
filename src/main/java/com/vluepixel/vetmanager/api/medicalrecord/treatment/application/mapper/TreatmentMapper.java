package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.CreateTreatmentRequest;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.UpdateTreatmentRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Treatment mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface TreatmentMapper
        extends CrudMapper<Treatment, TreatmentDto, Treatment.TreatmentBuilder> {
    @ObjectFactory
    default Treatment.TreatmentBuilder createTreatmentBuilder() {
        return Treatment.builder();
    }

    /**
     * Create treatment from request.
     *
     * @param request the create treatment request.
     * @return the treatment builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "record", source = "medicalRecordId")
    Treatment.TreatmentBuilder fromRequest(CreateTreatmentRequest request);

    /**
     * Update treatment from request.
     *
     * @param request the update treatment request.
     * @return the treatment builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "record", ignore = true)
    Treatment.TreatmentBuilder fromRequest(UpdateTreatmentRequest request);

    default MedicalRecord mapMedicalRecordIdToMedicalRecord(Long medicalRecordId) {
        return MedicalRecord.builder().id(medicalRecordId).build();
    }
}
