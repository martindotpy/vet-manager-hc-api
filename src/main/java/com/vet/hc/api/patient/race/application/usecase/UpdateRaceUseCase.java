package com.vet.hc.api.patient.race.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.application.port.in.UpdateRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.payload.UpdateRacePayload;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a race.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateRaceUseCase implements UpdateRacePort {
    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

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

            return Result.failure(RaceFailure.UNEXPECTED);
        }

        Race race = result.getSuccess();

        log.info("Race  with id {} updated", race.getId());

        return Result.success(raceMapper.toDto(race));
    }
}
