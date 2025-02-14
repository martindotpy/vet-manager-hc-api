package com.vluepixel.vetmanager.api.medicalrecord.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.CreateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.UpdateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

/**
 * Medical record mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface MedicalRecordMapper
        extends CrudMapper<MedicalRecord, MedicalRecordDto, MedicalRecord.MedicalRecordBuilder> {
    @ObjectFactory
    default MedicalRecord.MedicalRecordBuilder createMedicalRecordBuilder() {
        return MedicalRecord.builder();
    }

    /**
     * Create medical record from request.
     *
     * @param request the create medical record request.
     * @return the medical record builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "vet", source = "vetId")
    MedicalRecord.MedicalRecordBuilder fromRequest(CreateMedicalRecordRequest request);

    /**
     * Update medical record from request.
     *
     * @param request the update medical record request.
     * @return the medical record builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "vet", source = "vetId")
    MedicalRecord.MedicalRecordBuilder fromRequest(UpdateMedicalRecordRequest request);

    default Patient mapPatientIdToPatient(Long patientId) {
        return Patient.builder().id(patientId).build();
    }

    default User mapVetIdToUser(Long vetId) {
        return User.builder().id(vetId).build();
    }
}
