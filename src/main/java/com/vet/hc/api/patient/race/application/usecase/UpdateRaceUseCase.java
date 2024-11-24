package com.vet.hc.api.patient.race.application.usecase;

import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.application.port.in.UpdateRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.payload.UpdateRacePayload;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a race.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateRaceUseCase implements UpdateRacePort {
    private RaceRepository raceRepository;

    private final RaceMapper raceMapper = RaceMapper.INSTANCE;

    @Inject
    public UpdateRaceUseCase(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Result<RaceDto, RaceFailure> update(UpdateRacePayload payload) {
        log.info("Updating race with id {}", payload.getId());

        Race raceToUpdate = Race.builder()
                .id(payload.getId())
                .name(payload.getName())
                .species(Species.builder().id(payload.getSpeciesId()).build())
                .build();

        var result = raceRepository.save(raceToUpdate);

        if (result.isFailure()) {
            log.error("Error updating race : {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case DUPLICATED -> {
                    if (repositoryFailure.getField().equals("name"))
                        yield Result.failure(RaceFailure.DUPLICATED_NAME);

                    yield Result.failure(RaceFailure.UNEXPECTED);
                }
                default -> Result.failure(RaceFailure.UNEXPECTED);
            };
        }

        Race race = result.getSuccess();

        log.info("Race  with id {} updated", race.getId());

        return Result.success(raceMapper.toDto(race));
    }
}
