package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.port.in.AddMedicalHistoryToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.medicalhistory.application.port.in.CreateMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.payload.CreateMedicalHistoryPayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public final class AddMedicalHistoryToPatientUseCase implements AddMedicalHistoryToPatientPort {
    private CreateMedicalHistoryPort createMedicalHistoryPort;
    private FindPatientPort findPatientPort;

    @Inject
    public AddMedicalHistoryToPatientUseCase(
            CreateMedicalHistoryPort createMedicalHistoryPort,
            FindPatientPort findPatientPort) {
        this.createMedicalHistoryPort = createMedicalHistoryPort;
        this.findPatientPort = findPatientPort;
    }

    @Override
    public Result<PatientDto, PatientFailure> add(CreateMedicalHistoryPayload payload) {
        log.info("Adding medical history to patient with id `{}`", payload.getPatientId());

        var result = createMedicalHistoryPort.create(payload);

        if (result.isFailure()) {
            MedicalHistoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add medical history to patient with id `{}`", payload.getPatientId());

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
