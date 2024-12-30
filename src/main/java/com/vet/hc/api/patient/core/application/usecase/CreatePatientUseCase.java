package com.vet.hc.api.patient.core.application.usecase;

import java.time.LocalDateTime;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an patient .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreatePatientUseCase implements CreatePatientPort {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

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
            return Result.failure(PatientFailure.UNEXPECTED);
        }

        Patient createdPatient = result.getSuccess();

        log.info("Patient created: {}", createdPatient.getId());

        return Result.success(patientMapper.toDto(createdPatient));
    }
}
