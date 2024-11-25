package com.vet.hc.api.patient.core.application.usecase;

import java.time.LocalDate;
import java.util.Optional;

import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.UpdatePatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.core.domain.payload.UpdatePatientPayload;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an patient .
 */
@Slf4j
@NoArgsConstructor
public final class UpdatePatientUseCase implements UpdatePatientPort {
    private PatientRepository patientRepository;

    private final PatientMapper patientMapper = PatientMapper.INSTANCE;

    @Inject
    public UpdatePatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<PatientDto, PatientFailure> update(UpdatePatientPayload payload) {
        log.info("Updating patient with id {}", payload.getId());

        Optional<Patient> patient = patientRepository.findById(payload.getId());

        if (patient.isEmpty()) {
            log.error("Patient with id {} not found", payload.getId());

            return Result.failure(PatientFailure.NOT_FOUND);
        }

        Patient patientFound = patient.get();
        Patient patientToUpdate = Patient.builder()
                .id(payload.getId())
                .name(payload.getName())
                .birthDate(payload.getBirthDate())
                .age(LocalDate.now().getYear() - payload.getBirthDate().getYear())
                .characteristics(payload.getCharacteristics())
                .genre(payload.getGenre())
                .owner(Client.builder().id(payload.getOwnerId()).build())
                .race(Race.builder().id(payload.getRaceId()).build())
                .vaccines(patientFound.getVaccines())
                .medicalHistories(patientFound.getMedicalHistories())
                .build();

        var result = patientRepository.save(patientToUpdate);

        if (result.isFailure()) {
            log.error("Error updating patient : {}", result.getFailure());

            return Result.failure(PatientFailure.UNEXPECTED);
        }

        Patient patientUpdated = result.getSuccess();

        log.info("Patient with id {} updated", patientUpdated.getId());

        return Result.success(patientMapper.toDto(patientUpdated));
    }
}
