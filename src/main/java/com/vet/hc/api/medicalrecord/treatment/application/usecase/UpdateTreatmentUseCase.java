package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.UpdateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.UpdateTreatmentPayload;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a treatment.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateTreatmentUseCase implements UpdateTreatmentPort {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    @Override
    public Result<TreatmentDto, TreatmentFailure> update(UpdateTreatmentPayload payload) {
        log.info("Updating treatment with id `{}`", payload.getId());

        Treatment treatmentToUpdate = Treatment.builder()
                .id(payload.getId())
                .order(payload.getOrder())
                .description(payload.getDescription())
                .medicalRecord(MedicalRecord.builder().id(payload.getMedicalRecordId()).build())
                .build();

        var result = treatmentRepository.save(treatmentToUpdate);

        if (result.isFailure()) {
            log.error("Error updating treatment: {}", result.getFailure());

            return Result.failure(TreatmentFailure.UNEXPECTED);
        }

        Treatment treatmentUpdated = result.getSuccess();

        log.info("Treatment with id `{}` updated", treatmentUpdated.getId());

        return Result.success(treatmentMapper.toDto(treatmentUpdated));
    }
}
