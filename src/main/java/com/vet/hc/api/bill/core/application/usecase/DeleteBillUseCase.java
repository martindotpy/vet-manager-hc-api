package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.bill.core.application.port.in.DeleteBillPort;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an bill .
 */
@Slf4j
@NoArgsConstructor
public final class DeleteBillUseCase implements DeleteBillPort {
    private BillRepository billRepository;

    @Inject
    public DeleteBillUseCase(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Result<Void, BillFailure> deleteById(Long id) {
        log.info("Deleting bill with id: {}", id);

        var result = billRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Bill with id {} not found", id);

                return Result.failure(BillFailure.NOT_FOUND);
            }

            log.error("Error deleting bill with id {}", id, repositoryFailure);

            return Result.failure(BillFailure.UNEXPECTED);
        }

        log.info("Bill with id {} deleted", id);

        return Result.success();
    }
}
