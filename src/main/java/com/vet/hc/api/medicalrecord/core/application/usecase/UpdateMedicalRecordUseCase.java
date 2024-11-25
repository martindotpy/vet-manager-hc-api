package com.vet.hc.api.medicalrecord.core.application.usecase;

import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.medicalrecord.core.application.port.in.UpdateMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.core.domain.payload.UpdateMedicalRecordPayload;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a medical record.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateMedicalRecordUseCase implements UpdateMedicalRecordPort {
    private MedicalRecordRepository medicalRecordRepository;

    private final MedicalRecordMapper medicalRecordMapper = MedicalRecordMapper.INSTANCE;

    @Inject
    public UpdateMedicalRecordUseCase(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public Result<MedicalRecordDto, MedicalRecordFailure> update(UpdateMedicalRecordPayload payload) {
        log.info("Updating medical record with id `{}`", payload.getId());

        MedicalRecord medicalRecordToUpdate = MedicalRecord.builder()
                .id(payload.getId())
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
                .vet(User.builder().id(payload.getVetId()).build())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .build();

        var result = medicalRecordRepository.save(medicalRecordToUpdate);

        if (result.isFailure()) {
            log.error("Error updating medicalRecord: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                default -> Result.failure(MedicalRecordFailure.UNEXPECTED);
            };
        }

        MedicalRecord medicalRecordUpdated = result.getSuccess();

        log.info("MedicalRecord with id `{}` updated", medicalRecordUpdated.getId());

        return Result.success(medicalRecordMapper.toDto(medicalRecordUpdated));
    }
}
