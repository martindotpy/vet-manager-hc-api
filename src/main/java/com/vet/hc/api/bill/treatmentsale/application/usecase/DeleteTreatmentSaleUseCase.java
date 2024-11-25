package com.vet.hc.api.bill.treatmentsale.application.usecase;

import com.vet.hc.api.bill.treatmentsale.application.port.in.DeleteTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a treatmentSale.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteTreatmentSaleUseCase implements DeleteTreatmentSalePort {
    private TreatmentSaleRepository treatmentSaleRepository;

    @Inject
    public DeleteTreatmentSaleUseCase(TreatmentSaleRepository treatmentSaleRepository) {
        this.treatmentSaleRepository = treatmentSaleRepository;
    }

    @Override
    public Result<Void, TreatmentSaleFailure> deleteById(Long id) {
        log.info("Deleting treatmentSale with id: {}", id);

        var result = treatmentSaleRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("Treatment sale with id {} not found", id);

                    yield Result.failure(TreatmentSaleFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting treatment sale with id {}", id);

                    yield Result.failure(TreatmentSaleFailure.UNEXPECTED);
                }
            };
        }

        log.info("Treatment sale with id {} deleted", id);

        return Result.success();
    }
}
