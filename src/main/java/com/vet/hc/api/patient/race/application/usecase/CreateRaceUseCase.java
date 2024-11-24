package com.vet.hc.api.patient.race.application.usecase;

import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.application.port.in.CreateRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.payload.CreateRacePayload;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a race.
 */
@Slf4j
@NoArgsConstructor
public final class CreateRaceUseCase implements CreateRacePort {
    private RaceRepository raceRepository;

    private final RaceMapper raceMapper = RaceMapper.INSTANCE;

    @Inject
    public CreateRaceUseCase(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Result<RaceDto, RaceFailure> create(CreateRacePayload payload) {
        log.info("Creating race with name `{}`", payload.getName());

        Race race = Race.builder()
                .name(payload.getName())
                .species(Species.builder().id(payload.getSpeciesId()).build())
                .build();

        var result = raceRepository.save(race);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                case DUPLICATED -> {
                    if (failure.getField().equals("name")) {
                        log.error("The name of the race already in use `{}`", payload.getName());

                        yield Result.failure(RaceFailure.DUPLICATED_NAME);
                    }

                    yield Result.failure(RaceFailure.UNEXPECTED);
                }
                default -> {
                    log.error("Unexpected failure: {}", failure);

                    yield Result.failure(RaceFailure.UNEXPECTED);
                }
            };
        }

        Race createdRace = result.getSuccess();

        log.info("Race  created: {}", createdRace.getName());

        return Result.success(raceMapper.toDto(createdRace));
    }
}
