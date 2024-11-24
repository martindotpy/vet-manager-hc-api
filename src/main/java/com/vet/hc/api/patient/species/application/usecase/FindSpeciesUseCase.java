package com.vet.hc.api.patient.species.application.usecase;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.vet.hc.api.patient.species.adapter.out.mapper.SpeciesMapper;
import com.vet.hc.api.patient.species.application.port.in.FindSpeciesPort;
import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a species.
 */
@Slf4j
@NoArgsConstructor
public final class FindSpeciesUseCase implements FindSpeciesPort {
    private SpeciesRepository speciesRepository;

    private final SpeciesMapper speciesMapper = SpeciesMapper.INSTANCE;

    @Inject
    public FindSpeciesUseCase(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesDto> findAll() {
        log.info("Finding all species");

        return speciesRepository.findAll().stream()
                .map(speciesMapper::toDto)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    @Override
    public Result<SpeciesDto, SpeciesFailure> findById(Long id) {
        log.info("Finding species by id: {}", id);

        var result = speciesRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Species  not found with id: {}", id);

            return Result.failure(SpeciesFailure.NOT_FOUND);
        }

        log.info("Species  found: {}", result.get());

        return Result.success(speciesMapper.toDto(result.get()));
    }
}
