package com.vluepixel.vetmanager.api.vaccine.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.CreateVaccineRequest;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.UpdateVaccineRequest;

/**
 * Vaccine mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface VaccineMapper
        extends CrudMapper<Vaccine, VaccineDto, Vaccine.VaccineBuilder> {
    @ObjectFactory
    default Vaccine.VaccineBuilder createVaccineBuilder() {
        return Vaccine.builder();
    }

    /**
     * Create vaccine from request.
     *
     * @param request the create vaccine request.
     * @return the vaccine builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "vaccinator", source = "vaccinatorId")
    Vaccine.VaccineBuilder fromRequest(CreateVaccineRequest request);

    /**
     * Update vaccine from request.
     *
     * @param request the update vaccine request.
     * @return the vaccine builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "vaccinator", source = "vaccinatorId")
    Vaccine.VaccineBuilder fromRequest(UpdateVaccineRequest request);

    default Patient mapPatientIdToPatient(Long patientId) {
        return Patient.builder().id(patientId).build();
    }

    default User mapVaccinatorIdToUser(Long vaccinatorId) {
        return User.builder().id(vaccinatorId).build();
    }
}
