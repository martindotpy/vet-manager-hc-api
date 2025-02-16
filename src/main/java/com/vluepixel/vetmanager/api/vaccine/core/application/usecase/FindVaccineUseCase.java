package com.vluepixel.vetmanager.api.vaccine.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import java.util.List;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.application.mapper.VaccineMapper;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.FindVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.repository.VaccineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find vaccine use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindVaccineUseCase implements FindVaccinePort {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public List<VaccineDto> findAllByPatientId(Long patientId) {
        MDC.put("operationId", "Vaccine with patient id " + patientId);
        log.info("Finding all vaccine by patient id");

        List<Vaccine> vaccines = vaccineRepository.findAllBy(equal("patient.id", patientId));

        log.info("{} vaccines found",
                fgBrightGreen(vaccines.size()));

        return vaccines.stream().map(vaccineMapper::toDto).toList();
    }
}
