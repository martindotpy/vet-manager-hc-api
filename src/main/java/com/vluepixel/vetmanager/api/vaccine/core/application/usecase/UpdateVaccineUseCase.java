package com.vluepixel.vetmanager.api.vaccine.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;
import com.vluepixel.vetmanager.api.vaccine.core.application.mapper.VaccineMapper;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.UpdateVaccinePort;
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
    public VaccineDto update(UpdateVaccineRequest request) {
        MDC.put("operationId", "Vaccine id " + request.getId());
        log.info("Updating vaccine");

        // Update the vaccine
        var vaccineUpdated = vaccineMapper.fromRequest(request).build();
        vaccineUpdated = vaccineRepository.save(vaccineUpdated);

        return vaccineMapper.toDto(vaccineUpdated);
    }
}
