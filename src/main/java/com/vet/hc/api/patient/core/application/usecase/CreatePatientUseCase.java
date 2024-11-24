package com.vet.hc.api.patient.core.application.usecase;

import java.time.LocalDateTime;

import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.CreatePatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.core.domain.payload.CreatePatientPayload;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an patient .
 */
@Slf4j
@NoArgsConstructor
public final class CreatePatientUseCase implements CreatePatientPort {
    private PatientRepository patientRepository;

    private final PatientMapper patientMapper = PatientMapper.INSTANCE;

    @Inject
    public CreatePatientUseCase(
            PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<PatientDto, PatientFailure> create(CreatePatientPayload payload) {
        log.info("Creating patient with name `{}`", payload.getName());

        Patient patient = Patient.builder()
                .name(payload.getName())
                .birthDate(payload.getBirthDate())
                .characteristics(payload.getCharacteristics())
                .genre(payload.getGenre())
                .age(payload.getBirthDate().getYear() - LocalDateTime.now().getYear())
                .owner(Client.builder().id(payload.getOwnerId()).build())
                .race(Race.builder().id(payload.getRaceId()).build())
                .build();

        var result = patientRepository.save(patient);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to create patient with name `{}`", patient.getName());
                    yield Result.failure(PatientFailure.UNEXPECTED);
                }
            };
        }

        Patient createdPatient = result.getSuccess();

        log.info("Patient created: {}", createdPatient.getId());

        return Result.success(patientMapper.toDto(createdPatient));
    }
}
