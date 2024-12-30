package com.vet.hc.api.patient.race.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.application.port.in.CreateRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.payload.CreateRacePayload;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a race.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateRaceUseCase implements CreateRacePort {
    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

    @Override
    public Result<RaceDto, RaceFailure> create(CreateRacePayload payload) {
        log.info("Creating race with name `{}`", payload.getName());

        Race race = Race.builder()
                .name(payload.getName())
                .species(Species.builder().id(payload.getSpeciesId()).build())
                .build();

        var result = raceRepository.save(race);

        if (result.isFailure()) {
            return Result.failure(RaceFailure.UNEXPECTED);
        }

        Race createdRace = result.getSuccess();

        log.info("Race  created: {}", createdRace.getName());

        return Result.success(raceMapper.toDto(createdRace));
    }
}
