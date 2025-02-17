package com.vluepixel.vetmanager.api.patient.medicalhistory.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.CreateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.UpdateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Medical history mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface MedicalHistoryMapper
        extends CrudMapper<MedicalHistory, MedicalHistoryDto, MedicalHistory.MedicalHistoryBuilder> {
    /**
     * Creates a new {@link MedicalHistory} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default MedicalHistory.MedicalHistoryBuilder createMedicalHistoryBuilder() {
        return MedicalHistory.builder();
    }

    /**
     * Create medical history from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * <li><code>deleted</code></li>
     * <li><code>createdAt</code></li>
     * <li><code>updatedAt</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the create medical history request.
     * @return the medical history builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "patient", source = "patientId")
    MedicalHistory.MedicalHistoryBuilder fromRequest(CreateMedicalHistoryRequest request);

    /**
     * Update medical history from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>deleted</code></li>
     * <li><code>patient</code></li>
     * <li><code>createdAt</code></li>
     * <li><code>updatedAt</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the update medical history request.
     * @return the medical history builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MedicalHistory.MedicalHistoryBuilder fromRequest(UpdateMedicalHistoryRequest request);

    /**
     * Map patient id to patient.
     *
     * @param patientId the patient id.
     * @return the patient
     */
    default Patient mapPatientIdToPatient(Long patientId) {
        return Patient.builder().id(patientId).build();
    }
}
