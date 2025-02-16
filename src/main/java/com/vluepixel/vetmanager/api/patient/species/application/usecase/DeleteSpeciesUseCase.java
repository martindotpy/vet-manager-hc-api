package com.vluepixel.vetmanager.api.patient.species.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.species.application.port.in.DeleteSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete species use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteSpeciesUseCase implements DeleteSpeciesPort {
    private final SpeciesRepository speciesRepository;

    @Override
    @Transactional
    public void deleteById(Integer id) {
        MDC.put("operationId", "Species id " + id);
        log.info("Deleting species by id");

        speciesRepository.deleteById(id);

        log.info("Species deleted");
    }
}
