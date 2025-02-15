package com.vluepixel.vetmanager.api.patient.species.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.application.mapper.SpeciesMapper;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.FindSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find species use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindSpeciesUseCase implements FindSpeciesPort {
    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    public Paginated<SpeciesDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Species by criteria: " + fgBrightBlue(criteria.hashCode()));
        log.info("Finding species by {}",
                fgBrightBlue(criteria));

        Paginated<Species> paginatedSpecies = speciesRepository.findPaginatedBy(criteria);

        log.info("{} species found",
                fgBrightGreen(paginatedSpecies.getContent().size()));

        return paginatedSpecies.map(speciesMapper::toDto);
    }

    @Override
    public SpeciesDto findById(Integer id) {
        MDC.put("operationId", "Species id " + id);
        log.info("Finding species by id");

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Species.class, id));

        log.info("Species found");

        return speciesMapper.toDto(species);
    }
}
