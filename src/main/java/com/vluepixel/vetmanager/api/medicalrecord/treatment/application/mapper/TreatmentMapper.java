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
    /**
     * Creates a new {@link Treatment} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default Treatment.TreatmentBuilder createTreatmentBuilder() {
        return Treatment.builder();
    }

    /**
     * Create treatment from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * <li><code>deleted</code></li>
     * </ul>
     * </li>
     * </ul>
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
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>deleted</code></li>
     * <li><code>record</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the update treatment request.
     * @return the treatment builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "record", ignore = true)
    Treatment.TreatmentBuilder fromRequest(UpdateTreatmentRequest request);

    /**
     * Maps the medical record id to a medical record.
     *
     * @param medicalRecordId the medical record id.
     * @return the medical record
     */
    default MedicalRecord mapMedicalRecordIdToMedicalRecord(Long medicalRecordId) {
        return MedicalRecord.builder().id(medicalRecordId).build();
    }
}
