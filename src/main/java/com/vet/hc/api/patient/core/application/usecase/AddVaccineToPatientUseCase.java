package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.port.in.AddVaccineToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.vaccine.application.port.in.CreateVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.payload.CreateVaccinePayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public final class AddVaccineToPatientUseCase implements AddVaccineToPatientPort {
    private CreateVaccinePort createVaccinePort;
    private FindPatientPort findPatientPort;

    @Inject
    public AddVaccineToPatientUseCase(
            CreateVaccinePort createVaccinePort,
            FindPatientPort findPatientPort) {
        this.createVaccinePort = createVaccinePort;
        this.findPatientPort = findPatientPort;
    }

    @Override
    public Result<PatientDto, PatientFailure> add(CreateVaccinePayload payload) {
        log.info("Adding vaccine to patient with id `{}`", payload.getPatientId());

        var result = createVaccinePort.create(payload);

        if (result.isFailure()) {
            VaccineFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add vaccine to patient with id `{}`", payload.getPatientId());

                    yield Result.failure(PatientFailure.UNEXPECTED);
                }
            };
        }
        var resultFindPatient = findPatientPort.findById(payload.getPatientId());

        if (resultFindPatient.isFailure())
            return Result.failure(resultFindPatient.getFailure());

        return Result.success(resultFindPatient.getSuccess());
    }
}
