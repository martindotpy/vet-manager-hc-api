package com.vluepixel.vetmanager.api.patient.species.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.application.mapper.SpeciesMapper;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.UpdateSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.patient.species.domain.request.UpdateSpeciesRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update species use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateSpeciesUseCase implements UpdateSpeciesPort {
    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    @Transactional
    public SpeciesDto update(UpdateSpeciesRequest request) {
        MDC.put("operationId", "Species id " + request.getId());
        log.info("Updating species");

        Species speciesUpdated = speciesMapper.fromRequest(request).build();
        speciesUpdated = speciesRepository.save(speciesUpdated);

        log.info("Species updated");

        return speciesMapper.toDto(speciesUpdated);
    }
}
