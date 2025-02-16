package com.vluepixel.vetmanager.api.patient.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.patient.core.application.mapper.PatientMapper;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.CreatePatientPort;
import com.vluepixel.vetmanager.api.patient.core.domain.repository.PatientRepository;
import com.vluepixel.vetmanager.api.patient.core.domain.request.CreatePatientRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create patient use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreatePatientUseCase implements CreatePatientPort {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDto create(CreatePatientRequest request) {
        MDC.put("operationId", "Patient name " + request.getName());
        log.info("Creating patient");

        var newPatient = patientMapper.fromRequest(request).build();
        newPatient = patientRepository.save(newPatient);

        log.info("Patient created");

        return patientMapper.toDto(newPatient);
    }
}
