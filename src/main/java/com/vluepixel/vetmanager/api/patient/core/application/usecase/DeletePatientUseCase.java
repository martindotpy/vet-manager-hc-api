package com.vluepixel.vetmanager.api.patient.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.core.application.port.in.DeletePatientPort;
import com.vluepixel.vetmanager.api.patient.core.domain.repository.PatientRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete patient use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeletePatientUseCase implements DeletePatientPort {
    private final PatientRepository patientRepository;

    @Override
    public void deleteById(Long id) {
        MDC.put("operationId", "Patient id " + id);
        log.info("Deleting patient by id");

        patientRepository.deleteById(id);
    }
}
