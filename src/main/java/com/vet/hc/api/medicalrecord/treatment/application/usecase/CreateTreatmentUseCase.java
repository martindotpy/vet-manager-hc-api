package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.CreateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a treatment.
 */
@Slf4j
@NoArgsConstructor
public final class CreateTreatmentUseCase implements CreateTreatmentPort {
    private TreatmentRepository treatmentRepository;

    private final TreatmentMapper treatmentMapper = TreatmentMapper.INSTANCE;

    @Inject
    public CreateTreatmentUseCase(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public Result<TreatmentDto, TreatmentFailure> create(CreateTreatmentPayload payload) {
        Treatment treatment = Treatment.builder()
                .order(payload.getOrder())
                .description(payload.getDescription())
                .medicalRecord(MedicalRecord.builder().id(payload.getMedicalRecordId()).build())
                .build();

        var result = treatmentRepository.save(treatment);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Unexpected error creating treatment with medical record id `{}`",
                            payload.getMedicalRecordId());

                    yield Result.failure(TreatmentFailure.UNEXPECTED);
                }
            };
        }

        Treatment createdTreatment = result.getSuccess();

        log.info("Treatment with medical record id `{}` created", payload.getMedicalRecordId());

        return Result.success(treatmentMapper.toDto(createdTreatment));
    }
}
