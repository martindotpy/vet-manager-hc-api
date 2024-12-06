package com.vet.hc.api.patient.species.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.species.adapter.out.mapper.SpeciesMapper;
import com.vet.hc.api.patient.species.application.port.in.CreateSpeciesPort;
import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.patient.species.domain.payload.CreateSpeciesPayload;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a species.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateSpeciesUseCase implements CreateSpeciesPort {
    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    public Result<SpeciesDto, SpeciesFailure> create(CreateSpeciesPayload payload) {
        log.info("Creating species : {}", payload.getName());

        Species species = Species.builder()
                .name(payload.getName())
                .build();

        var result = speciesRepository.save(species);

        if (result.isFailure()) {
            log.error("Failed to create species : {}", result.getFailure().getMessage());

            RepositoryFailure failure = result.getFailure();

            if (failure == RepositoryFailure.DUPLICATED) {
                if (failure.getField().equals("name"))
                    return Result.failure(SpeciesFailure.DUPLICATED_NAME);
                else
                    return Result.failure(SpeciesFailure.UNEXPECTED);
            }

            return Result.failure(SpeciesFailure.UNEXPECTED);
        }

        Species createdSpecies = result.getSuccess();

        log.info("Species  created: {}", createdSpecies.getName());

        return Result.success(speciesMapper.toDto(createdSpecies));
    }
}
