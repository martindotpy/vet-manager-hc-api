package com.vluepixel.vetmanager.api.vaccine.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import java.util.List;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.application.mapper.VaccineMapper;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.FindVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.repository.VaccineRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find vaccine.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindVaccineUseCase implements FindVaccinePort {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public List<VaccineDto> findAllByPatientId(Long patientId) {
        MDC.put("operationId", "Vaccine of patient with id " + patientId);
        log.info("Finding all vaccine");

        var vaccines = vaccineRepository.findAllBy(like("patient.id", patientId));

        log.info("Retrieved {} vaccine ",
                fgBrightGreen(vaccines.size()));

        return vaccines.stream().map(vaccineMapper::toDto).toList();
    }

    @Override
    public VaccineDto findById(Long id) {
        MDC.put("operationId", "Vaccine id " + id);
        log.info("Finding vaccine by id");

        var vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Vaccine.class, id));

        log.info("Retrieved vaccine {}",
                fgBrightGreen(vaccine));

        return vaccineMapper.toDto(vaccine);
    }
}
