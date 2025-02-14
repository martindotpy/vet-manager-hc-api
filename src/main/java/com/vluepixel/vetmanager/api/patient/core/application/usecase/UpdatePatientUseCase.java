package com.vluepixel.vetmanager.api.patient.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.patient.core.application.mapper.PatientMapper;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.UpdatePatientPort;
import com.vluepixel.vetmanager.api.patient.core.domain.repository.PatientRepository;
import com.vluepixel.vetmanager.api.patient.core.domain.request.UpdatePatientRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update patient use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdatePatientUseCase implements UpdatePatientPort {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDto update(UpdatePatientRequest request) {
        MDC.put("operationId", "Patient id " + request.getId());
        log.info("Updating patient");

        // Update the patient
        var patientUpdated = patientMapper.fromRequest(request).build();
        patientUpdated = patientRepository.save(patientUpdated);

        return patientMapper.toDto(patientUpdated);
    }
}
