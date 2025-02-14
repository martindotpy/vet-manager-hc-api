package com.vluepixel.vetmanager.api.patient.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.client.core.domain.model.Client;
import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.patient.core.domain.request.CreatePatientRequest;
import com.vluepixel.vetmanager.api.patient.core.domain.request.UpdatePatientRequest;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Patient mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface PatientMapper
        extends CrudMapper<Patient, PatientDto, Patient.PatientBuilder> {
    @ObjectFactory
    default Patient.PatientBuilder createPatientBuilder() {
        return Patient.builder();
    }

    /**
     * Create patient from request.
     *
     * @param request the create patient request.
     * @return the patient builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "vaccines", ignore = true)
    @Mapping(target = "race", source = "raceId")
    @Mapping(target = "owner", source = "ownerId")
    Patient.PatientBuilder fromRequest(CreatePatientRequest request);

    /**
     * Update patient from request.
     *
     * @param request the update patient request.
     * @return the patient builder
     */
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "vaccines", ignore = true)
    @Mapping(target = "race", source = "raceId")
    @Mapping(target = "owner", source = "ownerId")
    Patient.PatientBuilder fromRequest(UpdatePatientRequest request);

    default Race mapRaceIdToRace(Integer raceId) {
        return Race.builder().id(raceId).build();
    }

    default Client mapOwnerIdToOwner(Long ownerId) {
        return Client.builder().id(ownerId).build();
    }
}
