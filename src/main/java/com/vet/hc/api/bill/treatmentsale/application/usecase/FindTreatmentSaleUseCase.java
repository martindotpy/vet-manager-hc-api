package com.vet.hc.api.bill.treatmentsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.treatmentsale.adapter.out.mapper.TreatmentSaleMapper;
import com.vet.hc.api.bill.treatmentsale.application.port.in.FindTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a treatmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindTreatmentSaleUseCase implements FindTreatmentSalePort {
    private final TreatmentSaleRepository treatmentSaleRepository;
    private final TreatmentSaleMapper treatmentSaleMapper;

    @Override
    public Result<TreatmentSaleDto, TreatmentSaleFailure> findById(Long id) {
        log.info("Finding treatment sale by id `{}`", id);

        var result = treatmentSaleRepository.findById(id);

        if (result.isEmpty()) {
            log.error("TreatmentSale not found with id `{}`", id);

            return Result.failure(TreatmentSaleFailure.NOT_FOUND);
        }

        TreatmentSale treatmentSale = result.get();

        log.info("Treatment sale with id `{}` found`", treatmentSale.getId());

        return Result.success(treatmentSaleMapper.toDto(treatmentSale));
    }
}
