package com.vluepixel.vetmanager.api.patient.race.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.patient.race.application.mapper.RaceMapper;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.FindRacePort;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.patient.race.domain.repository.RaceRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find race use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindRaceUseCase implements FindRacePort {
    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

    @Override
    public Paginated<RaceDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Races by criteria: " + fgBrightBlue(criteria.hashCode()));
        log.info("Finding races by {}",
                fgBrightBlue(criteria));

        Paginated<Race> paginatedRaces = raceRepository.findPaginatedBy(criteria);

        log.info("{} races found",
                fgBrightGreen(paginatedRaces.getContent().size()));

        return paginatedRaces.map(raceMapper::toDto);
    }

    @Override
    public RaceDto findById(Integer id) {
        MDC.put("operationId", "Race id " + id);
        log.info("Finding race by id");

        var race = raceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Race.class, id));

        log.info("Race found");

        return raceMapper.toDto(race);
    }
}
