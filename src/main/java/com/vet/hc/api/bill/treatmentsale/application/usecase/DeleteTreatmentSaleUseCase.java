package com.vet.hc.api.bill.treatmentsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.treatmentsale.application.port.in.DeleteTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a treatmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteTreatmentSaleUseCase implements DeleteTreatmentSalePort {
    private final TreatmentSaleRepository treatmentSaleRepository;

    @Override
    public Result<Void, TreatmentSaleFailure> deleteById(Long id) {
        log.info("Deleting treatmentSale with id: {}", id);

        var result = treatmentSaleRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        }

        log.info("Treatment sale with id {} deleted", id);

        return Result.success();
    }
}
