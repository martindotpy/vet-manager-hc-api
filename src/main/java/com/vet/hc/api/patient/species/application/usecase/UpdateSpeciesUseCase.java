package com.vet.hc.api.patient.species.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.species.adapter.out.mapper.SpeciesMapper;
import com.vet.hc.api.patient.species.application.port.in.UpdateSpeciesPort;
import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.patient.species.domain.payload.UpdateSpeciesPayload;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a species.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateSpeciesUseCase implements UpdateSpeciesPort {
    private final SpeciesRepository speciesRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    public Result<SpeciesDto, SpeciesFailure> update(UpdateSpeciesPayload payload) {
        log.info("Updating species with id {}", payload.getId());

        Species speciesToUpdate = Species.builder()
                .id(payload.getId())
                .name(payload.getName())
                .build();

        var result = speciesRepository.save(speciesToUpdate);

        if (result.isFailure()) {
            log.error("Error updating species : {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case DUPLICATED -> {
                    if (repositoryFailure.getField().equals("name"))
                        yield Result.failure(SpeciesFailure.DUPLICATED_NAME);

                    yield Result.failure(SpeciesFailure.UNEXPECTED);
                }
                default -> Result.failure(SpeciesFailure.UNEXPECTED);
            };
        }

        Species species = result.getSuccess();

        log.info("Species  with id {} updated", species.getId());

        return Result.success(speciesMapper.toDto(species));
    }
}
