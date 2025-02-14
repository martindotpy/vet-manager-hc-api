package com.vluepixel.vetmanager.api.patient.species.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.application.mapper.SpeciesMapper;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.CreateSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.patient.species.domain.request.CreateSpeciesRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create species use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateSpeciesUseCase implements CreateSpeciesPort {
    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    @Transactional
    public SpeciesDto create(CreateSpeciesRequest request) {
        MDC.put("operationId", "Species name " + request.getName());
        log.info("Creating species");

        // Save the species
        var newSpecies = speciesMapper.fromRequest(request).build();
        newSpecies = speciesRepository.save(newSpecies);

        return speciesMapper.toDto(newSpecies);
    }
}
