package com.vluepixel.vetmanager.api.patient.race.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.patient.race.application.mapper.RaceMapper;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.CreateRacePort;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.patient.race.domain.repository.RaceRepository;
import com.vluepixel.vetmanager.api.patient.race.domain.request.CreateRaceRequest;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create race use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateRaceUseCase implements CreateRacePort {
    private final SpeciesRepository speciesRepository;

    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

    @Override
    @Transactional
    public RaceDto create(CreateRaceRequest request) {
        MDC.put("operationId", "Race name " + request.getName());
        log.info("Creating race");

        Race newRace = raceMapper.fromRequest(request).build();
        newRace = raceRepository.save(newRace);

        // Get the species name
        Species species = speciesRepository.findById(newRace.getSpecies().getId()).get();
        newRace = raceMapper.toBuilder(newRace).species(species).build();

        log.info("Race created");

        return raceMapper.toDto(newRace);
    }
}
