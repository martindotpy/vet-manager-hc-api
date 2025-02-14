package com.vluepixel.vetmanager.api.vaccine.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.application.mapper.VaccineMapper;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.UpdateVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.repository.VaccineRepository;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.UpdateVaccineRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update vaccine use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateVaccineUseCase implements UpdateVaccinePort {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    @Transactional
    public VaccineDto update(Long patientId, UpdateVaccineRequest request) {
        MDC.put("operationId", "Vaccine id " + request.getId());
        log.info("Updating vaccine");

        // Update the vaccine
        var vaccineUpdated = vaccineMapper.fromRequest(request).build();
        int rowsModified = vaccineRepository.updateBy(
                Criteria.of(
                        equal("id", request.getId()),
                        equal("patient.id", patientId)),
                FieldUpdate.set("name", vaccineUpdated.getName()),
                FieldUpdate.set("doseInMilliliters", vaccineUpdated.getDoseInMilliliters()),
                FieldUpdate.set("providedAt", vaccineUpdated.getProvidedAt()),
                FieldUpdate.set("vaccinator", vaccineUpdated.getVaccinator()));

        if (rowsModified == 0) {
            throw new NotFoundException(Vaccine.class, request.getId());
        } else if (rowsModified > 1) {
            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Vaccine with patient id '" +
                                    patientId +
                                    "' and id '" +
                                    request.getId() +
                                    "' has more than one row modified"));
        }

        vaccineUpdated = vaccineRepository.findById(request.getId()).get();

        return vaccineMapper.toDto(vaccineUpdated);
    }
}
