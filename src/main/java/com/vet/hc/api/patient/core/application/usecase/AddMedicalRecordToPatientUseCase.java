package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.application.port.in.CreateMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.payload.CreateMedicalRecordPayload;
import com.vet.hc.api.patient.core.application.port.in.AddMedicalRecordToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class AddMedicalRecordToPatientUseCase implements AddMedicalRecordToPatientPort {
    private final CreateMedicalRecordPort createMedicalRecordPort;
    private final FindPatientPort findPatientPort;

    @Override
    public Result<PatientDto, PatientFailure> add(CreateMedicalRecordPayload payload) {
        log.info("Adding medical record to patient with id `{}`", payload.getPatientId());

        var result = createMedicalRecordPort.create(payload);

        if (result.isFailure()) {
            MedicalRecordFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add medical record to patient with id `{}`", payload.getPatientId());

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
