package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.application.port.in.DeleteBillPort;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an bill .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteBillUseCase implements DeleteBillPort {
    private final BillRepository billRepository;

    @Override
    public Result<Void, BillFailure> deleteById(Long id) {
        log.info("Deleting bill with id: {}", id);

        var result = billRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(BillFailure.UNEXPECTED);
        }

        log.info("Bill with id {} deleted", id);

        return Result.success();
    }
}
