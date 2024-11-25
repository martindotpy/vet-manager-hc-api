package com.vet.hc.api.medicalrecord.core.application.usecase;

import com.vet.hc.api.medicalrecord.core.application.port.in.AddTreatmentToMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.application.port.in.FindMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.CreateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public final class AddTreatmentToMedicalRecordUseCase implements AddTreatmentToMedicalRecordPort {
    private CreateTreatmentPort createTreatmentPort;
    private FindMedicalRecordPort findMedicalRecordPort;

    @Inject
    public AddTreatmentToMedicalRecordUseCase(
            CreateTreatmentPort createTreatmentPort,
            FindMedicalRecordPort findMedicalRecordPort) {
        this.createTreatmentPort = createTreatmentPort;
        this.findMedicalRecordPort = findMedicalRecordPort;
    }

    @Override
    public Result<MedicalRecordDto, MedicalRecordFailure> add(CreateTreatmentPayload payload) {
        log.info("Adding treatment to medical record with id `{}`", payload.getMedicalRecordId());

        var result = createTreatmentPort.create(payload);

        if (result.isFailure()) {
            TreatmentFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add treatment to medical record with id `{}`", payload.getMedicalRecordId());

                    yield Result.failure(MedicalRecordFailure.UNEXPECTED);
                }
            };
        }
        var resultFindMedicalRecord = findMedicalRecordPort.findById(payload.getMedicalRecordId());

        if (resultFindMedicalRecord.isFailure())
            return Result.failure(resultFindMedicalRecord.getFailure());

        return Result.success(resultFindMedicalRecord.getSuccess());
    }
}