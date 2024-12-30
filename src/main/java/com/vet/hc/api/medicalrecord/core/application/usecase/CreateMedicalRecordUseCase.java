package com.vet.hc.api.medicalrecord.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.medicalrecord.core.application.port.in.CreateMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.core.domain.payload.CreateMedicalRecordPayload;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.model.UserImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a medical record.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateMedicalRecordUseCase implements CreateMedicalRecordPort {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public Result<MedicalRecordDto, MedicalRecordFailure> create(CreateMedicalRecordPayload payload) {
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .entryTime(payload.getEntryTime())
                .reason(payload.getReason())
                .physicalExamination(payload.getPhysicalExamination())
                .celsiusTemperature(payload.getCelsiusTemperature())
                .respiratoryRate(payload.getRespiratoryRate())
                .heartRate(payload.getHeartRate())
                .weight(payload.getWeight())
                .sterilized(payload.isSterilized())
                .supplementaryExamination(payload.getSupplementaryExamination())
                .recipe(payload.getRecipe())
                .diagnosis(payload.getDiagnosis())
                .vet(UserImpl.builder().id(payload.getVetId()).build())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .build();

        var result = medicalRecordRepository.save(medicalRecord);

        if (result.isFailure()) {
            return Result.failure(MedicalRecordFailure.UNEXPECTED);
        }

        MedicalRecord createdMedicalRecord = result.getSuccess();

        log.info("MedicalRecord with patient id `{}` created", payload.getPatientId());

        return Result.success(medicalRecordMapper.toDto(createdMedicalRecord));
    }
}
