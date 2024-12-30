package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.CreateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a treatment.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateTreatmentUseCase implements CreateTreatmentPort {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    @Override
    public Result<TreatmentDto, TreatmentFailure> create(CreateTreatmentPayload payload) {
        Treatment treatment = Treatment.builder()
                .order(payload.getOrder())
                .description(payload.getDescription())
                .medicalRecord(MedicalRecord.builder().id(payload.getMedicalRecordId()).build())
                .build();

        var result = treatmentRepository.save(treatment);

        if (result.isFailure()) {
            return Result.failure(TreatmentFailure.UNEXPECTED);
        }

        Treatment createdTreatment = result.getSuccess();

        log.info("Treatment with medical record id `{}` created", payload.getMedicalRecordId());

        return Result.success(treatmentMapper.toDto(createdTreatment));
    }
}
