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
 *
 * @see Vaccine
 * @see VaccineDto
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface VaccineMapper
        extends CrudMapper<Vaccine, VaccineDto, Vaccine.VaccineBuilder> {
    /**
     * Creates a new {@link Vaccine} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default Vaccine.VaccineBuilder createVaccineBuilder() {
        return Vaccine.builder();
    }

    /**
     * Create vaccine from create vaccine request.
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
     * @param request the create vaccine request.
     * @return the vaccine builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "vaccinator", source = "vaccinatorId")
    Vaccine.VaccineBuilder fromRequest(CreateVaccineRequest request);

    /**
     * Create vaccine from update vaccine request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>deleted</code></li>
     * <li><code>patient</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the update vaccine request.
     * @return the vaccine builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "vaccinator", source = "vaccinatorId")
    Vaccine.VaccineBuilder fromRequest(UpdateVaccineRequest request);

    /**
     * Map patient id to patient.
     *
     * @param patientId the patient id.
     * @return the patient
     */
    default Patient mapPatientIdToPatient(Long patientId) {
        return Patient.builder().id(patientId).build();
    }

    /**
     * Map vaccinator id to user.
     *
     * @param vaccinatorId the vaccinator id.
     * @return the user
     */
    default User mapVaccinatorIdToUser(Long vaccinatorId) {
        return User.builder().id(vaccinatorId).build();
    }
}
