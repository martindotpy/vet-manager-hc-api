package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.FindTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a treatment.
 */
@Slf4j
@NoArgsConstructor
public final class FindTreatmentUseCase implements FindTreatmentPort {
    private TreatmentRepository medicalrecordRepository;

    private final TreatmentMapper medicalrecordMapper = TreatmentMapper.INSTANCE;

    @Inject
    public FindTreatmentUseCase(TreatmentRepository medicalrecordRepository) {
        this.medicalrecordRepository = medicalrecordRepository;
    }

    @Override
    public Result<TreatmentDto, TreatmentFailure> findById(Long id) {
        log.info("Finding treatment by id `{}`", id);

        var result = medicalrecordRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Treatment not found with id `{}`", id);

            return Result.failure(TreatmentFailure.NOT_FOUND);
        }

        Treatment treatment = result.get();

        log.info("Treatment with id `{}` found`", treatment.getId());

        return Result.success(medicalrecordMapper.toDto(treatment));
    }
}
