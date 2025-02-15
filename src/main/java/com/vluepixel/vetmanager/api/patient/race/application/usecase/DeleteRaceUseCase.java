package com.vluepixel.vetmanager.api.patient.race.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.race.application.port.in.DeleteRacePort;
import com.vluepixel.vetmanager.api.patient.race.domain.repository.RaceRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete race use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteRaceUseCase implements DeleteRacePort {
    private final RaceRepository raceRepository;

    @Override
    public void deleteById(Integer id) {
        MDC.put("operationId", "Race id " + id);
        log.info("Deleting race by id");

        raceRepository.deleteById(id);

        log.info("Race deleted");
    }
}
