package com.vet.hc.api.patient.race.application.usecase;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.application.port.in.FindRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a race.
 */
@Slf4j
@NoArgsConstructor
public final class FindRaceUseCase implements FindRacePort {
    private RaceRepository raceRepository;

    private final RaceMapper raceMapper = RaceMapper.INSTANCE;

    @Inject
    public FindRaceUseCase(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public List<RaceDto> findAll() {
        log.info("Finding all races");

        return raceRepository.findAll().stream()
                .map(raceMapper::toDto)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    @Override
    public Result<RaceDto, RaceFailure> findById(Long id) {
        log.info("Finding race by id: {}", id);

        var result = raceRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Race  not found with id: {}", id);

            return Result.failure(RaceFailure.NOT_FOUND);
        }

        log.info("Race  found: {}", result.get());

        return Result.success(raceMapper.toDto(result.get()));
    }
}
